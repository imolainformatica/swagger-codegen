package io.swagger.codegen.languages;

import io.swagger.codegen.*;
import io.swagger.codegen.languages.features.BeanValidationFeatures;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.auth.SecuritySchemeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaApacheCamelServerCodegen extends AbstractJavaCodegen implements CodegenConfig {

    static Logger LOGGER = LoggerFactory.getLogger(JavaApacheCamelServerCodegen.class);
    protected static final String HTTP = "http";
    protected static final String DEFAULT_HOST = "127.0.0.1";
    protected static final String DEFAULT_PORT = "80";
    protected boolean useBeanValidation = Boolean.TRUE;
    public static final String GENERATE_POM = "generatePom";

    private boolean generatePom = Boolean.FALSE;

    public CodegenType getTag() {
        return CodegenType.SERVER;
    }

    public String getName() {
        return "java-apache-camel";
    }

    public String getHelp() {
        return "Generates a java-apache-camel server.";
    }

    public JavaApacheCamelServerCodegen() {
        super();

        outputFolder = "generated-code" + File.separator + "java-apache-camel";
        modelTemplateFiles.put("model.mustache", ".java");
        apiTemplateFiles.put("api.mustache", ".java");

        embeddedTemplateDir = templateDir = "java-apache-camel";
        apiPackage = "io.swagger.api";
        modelPackage = "io.swagger.model";
        invokerPackage = "io.swagger";

        dateLibrary = "legacy";

        apiTestTemplateFiles.clear();
        modelTestTemplateFiles.clear();
        modelDocTemplateFiles.remove("model_doc.mustache");
        apiDocTemplateFiles.remove("api_doc.mustache");

        additionalProperties.put("jackson", "true");


        cliOptions.add(CliOption.newBoolean(GENERATE_POM, "Whether to generate pom.xml if the file does not already exist.").defaultValue(String.valueOf(generatePom)));
        cliOptions.add(CliOption.newBoolean(BeanValidationFeatures.USE_BEANVALIDATION, "Use BeanValidation API annotations"));


    }

    @Override
    public void addOperationToGroup(String tag, String resourcePath, Operation operation, CodegenOperation co, Map<String, List<CodegenOperation>> operations) {
        co.httpMethod = co.httpMethod.toLowerCase();
        String basePath = resourcePath;
        if (basePath.startsWith("/")) {
            basePath = basePath.substring(1);
        }
        int pos = basePath.indexOf("/");
        if (pos > 0) {
            basePath = basePath.substring(0, pos);
        }
        if (basePath == "") {
            basePath = "default";
        } else {
            if (co.path.startsWith("/" + basePath)) {
                co.path = co.path.substring(("/" + basePath).length());
            }
            co.subresourceOperation = !co.path.isEmpty();
        }
        List<CodegenOperation> opList = operations.get(basePath);
        if (opList == null) {
            opList = new ArrayList<CodegenOperation>();
            operations.put(basePath, opList);
        }
        opList.add(co);
        co.baseName = basePath;
    }

    @Override
    public List<CodegenSecurity> fromSecurity(Map<String, SecuritySchemeDefinition> schemes) {
        List<String> schemesList = new ArrayList<>();
        if (schemes != null) {
            for (String scheme : schemes.keySet()) {
                schemesList.add(scheme);
            }
        }
        if (schemesList.isEmpty()) {
            schemesList.add(HTTP);
        }
        additionalProperties.put("schemes", schemesList);
        return super.fromSecurity(schemes);
    }

    @Override
    public void processOpts() {
        super.processOpts();

        if (useBeanValidation) {
            apiTemplateFiles.put("apiValidator.mustache", "Validator.java");
            writePropertyBack(BeanValidationFeatures.USE_BEANVALIDATION, useBeanValidation);
        }
        if (additionalProperties.containsKey(GENERATE_POM)) {
            generatePom = Boolean.valueOf(additionalProperties.get(GENERATE_POM).toString());
        }

        if (generatePom) {
            apiTemplateFiles.put("apiRouteBuilder.mustache", "RouteBuilder.java");
            writeOptional(outputFolder, new SupportingFile("pom.mustache", "", "pom.xml"));
            writeOptional((sourceFolder + '/' + invokerPackage).replace(".", "/"),
                    new SupportingFile("application.mustache",
                            (sourceFolder + '/' + invokerPackage).replace(".", "/"),
                            "Application.java"));
            writeOptional(projectFolder + "/resources/static",
                    new SupportingFile("index.mustache",
                            projectFolder + "/resources/static",
                            "index.html"));

        }
    }

    @Override
    public Map<String, Object> postProcessOperations(Map<String, Object> objs) {
        SupportingFile apiConfig = new SupportingFile("apiConfiguration.mustache",
                (sourceFolder + '/' + apiPackage).replace(".", "/"),
                "ApiConfiguration.java");
        writeOptional((sourceFolder + '/' + apiPackage).replace(".", "/"), apiConfig);
        return super.postProcessOperations(objs);
    }

    @Override
    public void preprocessSwagger(Swagger swagger) {
        super.preprocessSwagger(swagger);
        String host = swagger.getHost() != null ? swagger.getHost() : DEFAULT_HOST;
        String port = DEFAULT_PORT;
        if (host != null) {
            String[] parts = host.split(":");
            if (parts.length > 1) {
                host = parts[0];
                port = parts[1];
            }
        }

        this.additionalProperties.put("serverPort", port);
        this.additionalProperties.put("serverHost", host);
    }
}
