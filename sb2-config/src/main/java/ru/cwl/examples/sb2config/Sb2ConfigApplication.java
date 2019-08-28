package ru.cwl.examples.sb2config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@SpringBootApplication
public class Sb2ConfigApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Sb2ConfigApplication.class, args);
        AppConfig cfg = ctx.getBean(AppConfig.class);
        System.out.println(cfg);

        String bean1=null;
        if(ctx.containsBean("s1")) {
            bean1 = ctx.getBean("s1", String.class);
        }

        String bean2=null;
        if(ctx.containsBean("s2")) {
            bean2 = ctx.getBean("s2", String.class);
        }
        System.out.printf("s1: %s s2: %s\n",bean1,bean2);

    }

    @Bean
    @ConditionalOnProperty(prefix = "app.run-services", name = "bbb", matchIfMissing = true)
    String s1(){
        return "S1";
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.run-services", name = "aaa", matchIfMissing = true)
    String s2(){
        return "S2";
    }

}
