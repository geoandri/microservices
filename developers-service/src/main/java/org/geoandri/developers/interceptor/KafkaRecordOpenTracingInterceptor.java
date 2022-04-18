package org.geoandri.developers.interceptor;

import io.opentracing.SpanContext;
import io.opentracing.contrib.interceptors.OpenTracingInterceptor;
import io.opentracing.contrib.kafka.TracingKafkaUtils;
import io.opentracing.util.GlobalTracer;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import org.apache.kafka.common.header.Headers;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.geoandri.developers.annotation.KafkaConsumerInterceptor;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Priority(10)
@KafkaConsumerInterceptor
public class KafkaRecordOpenTracingInterceptor {
    @AroundInvoke
    public Object propagateSpanCtx(InvocationContext ctx) throws Exception {
        for (int i = 0 ; i < ctx.getParameters().length ; i++) {
            Object parameter = ctx.getParameters()[i];

            if (parameter instanceof Message) {
                Message message = (Message) parameter;

                Headers headers = (Headers) message.getMetadata(IncomingKafkaRecordMetadata.class)
                        .map(s -> ((IncomingKafkaRecordMetadata) s).getHeaders())
                        .get();
                SpanContext spanContext = getSpanContext(headers);
                ctx.getContextData().put(OpenTracingInterceptor.SPAN_CONTEXT, spanContext);
            }
        }

        return ctx.proceed();
    }

    private SpanContext getSpanContext(Headers headers) {
        return TracingKafkaUtils.extractSpanContext(headers, GlobalTracer.get());
    }
}
