package com.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.app")
@EnableTransactionManagement
public class SpringConfig {

/*    @Bean
    public DataSource dataSource(){
        try {
            EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
            return dbBuilder.setType(EmbeddedDatabaseType.H2).addScripts()
        }
    }*/

    /*    private Properties hibernateProperties() throws IOException {
            Properties hibernateProp = new Properties();
            hibernateProp.load(new FileInputStream(new File("C:\\IDEA_Projects\\nc-unc-2021\\target\\classes\\META-INF\\persistence.xml")));
            return hibernateProp;
        }

        @Bean
        public SessionFactory sessionFactory() throws IOException {
            LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
            sessionFactoryBean.setPackagesToScan("com.app.model");
            sessionFactoryBean.setHibernateProperties(hibernateProperties());
            sessionFactoryBean.afterPropertiesSet();
            return sessionFactoryBean.getObject();
        }

        @Bean
        public PlatformTransactionManager transactionManager() throws IOException {
            return new HibernateTransactionManager();
        }*/
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
/*        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("ubuntu");
        dataSource.setUrl("jdbc:mysql://localhost:3306/data_base");
        dataSource.setMaxActive(10);
        dataSource.setMaxIdle(5);
        dataSource.setInitialSize(5);
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;*/
        try {
            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName("com.mysql.jdbc.Driver");
            dataSource.setDriverClass(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        } catch (Exception e) {
            return null;
        }
/*        try {
            EmbeddedDatabaseBuilder dbBuilder =
                    new EmbeddedDatabaseBuilder();
            return dbBuilder.setType(EmbeddedDatabaseType.H2)
                    .addScripts("classpath:db/h2/schema.sql",
                            "classpath:db/h2/test-data.sql").build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/

    }

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        //             <property name="hibernate.connection.driver_class" value="com.mysql.jdbc.Driver" />
        //            <property name="hibernate.connection.url" value="jdbc:mysql://localhost:3306/data_base"/>
        //            <property name="hibernate.connection.username" value="root"/>
        //            <property name="hibernate.connection.password" value="ubuntu"/>
        //            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/> <!-- DB Dialect -->
        //            <property name="hibernate.show_sql" value="true" /> <!-- Show SQL in console -->
        //            <property name="hibernate.hbm2ddl.auto" value="update" /> <!-- create / create-drop / update -->
        //            <property name="hibernate.format_sql" value="true" /> <!-- Show SQL formatted -->
/*        hibernateProp.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        hibernateProp.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/data_base");
        hibernateProp.put("hibernate.connection.username", "root");
        hibernateProp.put("hibernate.connection.password", "ubuntu");*/
        hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProp.put("hibernate.show_sql", "true");
        hibernateProp.put("hibernate.hbm2ddl.auto", "update");
        hibernateProp.put("hibernate.format_sql", "true");
        return hibernateProp;
    }

/*    @Bean
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.app.model");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws IOException {
        return new HibernateTransactionManager(sessionFactory());
    }*/



/*    @Bean
    public EntityManager entityManager() {
        EntityManager entityManager =
                Persistence.createEntityManagerFactory("AbstractRepository").createEntityManager();
        return entityManager;
    }*/

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("com.app.model");
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.afterPropertiesSet();
        return factoryBean.getNativeEntityManagerFactory();
    }




/*    @Bean
    public Properties hibernateProperties() throws IOException {
        Properties hibernateProp = new Properties();
        hibernateProp.load(new FileInputStream(new File("C:\\IDEA_Projects\\nc-unc-2021\\target\\classes\\META-INF\\persistence.xml")));
        return hibernateProp;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws IOException {
        return new JpaTransactionManager(entityManagerFactory());
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }


    @Bean
    public EntityManagerFactory entityManagerFactory() throws IOException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("com.app.model");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.afterPropertiesSet();
        return factoryBean.getNativeEntityManagerFactory();
    }*/

}
