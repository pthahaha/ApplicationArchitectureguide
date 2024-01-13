package com.kakao.www.applicationarchitectureguide.gRPC.service;

import com.kakao.www.product.*;
import org.springframework.stereotype.Service;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.logging.Logger;

@Service
public class StubService {
    private static final Logger logger = Logger.getLogger(StubService.class.getName());

    public void productInfoClient(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        ProductInfoGrpc.ProductInfoBlockingStub stub =
                ProductInfoGrpc.newBlockingStub(channel);

        ProductInfoOuterClass.ProductID productID = stub.addProduct(
                ProductInfoOuterClass.Product.newBuilder()
                        .setName("Samsung S10")
                        .setDescription("Samsung Galaxy S50 is the latest smart phone, " +
                                "launched in February 2050")
                        .setPrice(70000)
                        .build());
        logger.info("Product ID: " + productID.getValue() + " added successfully.");

        ProductInfoOuterClass.Product product = stub.getProduct(productID);
        logger.info("Product: " + product.toString());
        channel.shutdown();
    }

}
