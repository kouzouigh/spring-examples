package com.spring.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

//@ComponentScan // instead of explicitly defining the bean let Spring do that by setting @Component on the classes
//@Configuration
@SpringBootApplication
public class Config101Application {

    /**
     * Register bean on some conditions or some sort of dynamic state with callbacks which
     * gives us a chance to register objects dynamically
     */
    //@Component
    public static class MyBDRPP implements BeanDefinitionRegistryPostProcessor {

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            final BeanFactory beanFactory = (BeanFactory) registry;
            registry.registerBeanDefinition(
                    "barService",
                    BeanDefinitionBuilder.genericBeanDefinition(BarService.class).getBeanDefinition());

            // here we define how should the object FooService will be instantiated with the Supplier
            registry.registerBeanDefinition(
                    "fooService",
                    BeanDefinitionBuilder.genericBeanDefinition(
                            FooService.class, () -> new FooService(beanFactory.getBean(BarService.class))).getBeanDefinition()
            );
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        }
    }

    public static void main(String[] args) {
        // Unit tests isn't going to run this main method, it's going to have it's own context. for this to work
        // we will provide an application initializer (ProgrammaticBeanDefinitionInitializr)
        //AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Config101Application.class);
        //Registering beans with Application context programmatically, it won't work on tests because test don't run this method
        //ac.registerBean(...);
        // The solution was to create ApplicationContextInitializer and register beans there!

        // this is a shortcut of new SpringApplication(Config101Application.class, args)
        //SpringApplication.run(Config101Application.class, args);

        // adding this would not be seen in tests, we want to have a single place where we can define the initializers in both the main code and tests
        // we can achieve that with spring.factories
        // new SpringApplication(Config101Application.class).addInitializers(new ProgrammaticBeanDefinitionInitializr());



    }
}

class ProgrammaticBeanDefinitionInitializr implements ApplicationContextInitializer<GenericApplicationContext> {

    // we are going to tell spring to call this method when the application starts up
    @Override
    public void initialize(GenericApplicationContext applicationContext) {
        // registering bean
        BarService barService = new BarService();
        FooService fooService = new FooService(barService);
        applicationContext.registerBean(BarService.class, () -> barService);
        applicationContext.registerBean(FooService.class, () -> fooService);
    }
}

class FooService {
    private final BarService barService;

    FooService(BarService barService) {
        this.barService = barService;
    }
}

class BarService {

}
