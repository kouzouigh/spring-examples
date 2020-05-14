### Video:
https://www.youtube.com/watch?v=Q_P28p7XsbQ&list=PLsUM2ScMfp-p5lLeYeRdpLPEQWAzqzYGM&index=67

### Resume:
In this video, we learn how to create Spring Beans programmatically, and there are different ways:
1. By using _@ComponentScan_ and _@Configuration_ on a Configuration file, and @Component on Beans Classes we let Spring creating beans.
2. By explicitly defining beans by using _@Beans_ on a configuration Class.
3. By using _BeanDefinitionRegistryPostProcessor_ we can create our beans programmatically, this is useful if we want to create beans on some conditions or some sort of dynamic state with callbacks which gives us a chance to register objects dynamically.
4. by using _ApplicationContextInitializer_ (Spring 5 feature)
