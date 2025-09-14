package com.example.orderservice.api.grpc
import com.example.orderservice.proto.CreateOrderRequest
import com.example.orderservice.proto.GetOrderRequest
import com.example.orderservice.proto.OrderResponse
import com.example.orderservice.proto.OrderServiceGrpc
import com.example.orderservice.service.OrderService
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import java.util.UUID

@GrpcService
class OrderGrpcService(
    private val orderService: OrderService
) : OrderServiceGrpc.OrderServiceImplBase() {

    override fun createOrder(
        request: CreateOrderRequest,
        responseObserver: StreamObserver<OrderResponse>
    ) {
        val response = orderService.createOrder(
            UUID.fromString(request.cartId),
            UUID.fromString(request.clientId),
            request.currency
        )
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getOrderById(
        request: GetOrderRequest,
        responseObserver: StreamObserver<OrderResponse>
    ) {
        val response = orderService.getOrderById(UUID.fromString(request.orderId))
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}