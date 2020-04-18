package com.jeremiahseagraves.dou.k8s.helloworld.component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class HelloLambdaInvoker {

    @Value("${aws.functionName}")
    private String FUNCTION_NAME;

    @Value("${aws.region}")
    private String REGION;

    @Value("${aws.accessKeyId}")
    private String ACCESS_KEY;

    @Value("${aws.secretKey}")
    private String SECRET_KEY;

    public String invokeHello(String name) {
        final String payload = "{\"name\": \"" + name + "\"}";
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(FUNCTION_NAME)
                .withPayload(payload);
        InvokeResult invokeResult = null;

        try {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .withRegion(REGION).build();

            log.info("Invocation with payload: {}", payload);

            invokeResult = awsLambda.invoke(invokeRequest);

            final String response = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

            log.info("Lambda response: {}", response);

            return response.replaceAll("\"", ""); //lambda returns double quoted string, don't know why

        } catch (ServiceException e) {
            return "";
        }
    }
}
