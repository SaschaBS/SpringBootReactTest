package de.lineas.demo.config

import de.lineas.demo.model.Person
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.item.file.transform.LineTokenizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class BatchConfiguration {

    @Autowired
    var jobBuilderFactory: JobBuilderFactory? = null

    @Autowired
    var stepBuilderFactory: StepBuilderFactory? = null

    @Autowired
    var dataSource: DataSource? = null

    @Bean
    fun reader(): FlatFileItemReader<Person> {
        val reader = FlatFileItemReader<Person>()
        reader.setResource(ClassPathResource("us-500.csv"))
        reader.setLineMapper(object : DefaultLineMapper<Person>() {
            init {
                setLineTokenizer(object : DelimitedLineTokenizer() {
                    init {
                        setNames(arrayOf("first_name", "last_name", "company_name", "address", "city", "county", "state", "zip", "phone1", "phone2", "email", "web"))
                    }
                })
                setFieldSetMapper(object : BeanWrapperFieldSetMapper<Person>() {
                    init {
                        setTargetType(Person::class.java)
                    }
                })
            }
        })
        return reader
    }

    @Bean
    fun writer(): JdbcBatchItemWriter<Person> {
        val writer = JdbcBatchItemWriter<Person>()
        writer.setItemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider<Person>())
        writer.setSql("INSERT INTO person (id, first_name, last_name, company_name, address, city, county, state, zip, phone1, phone2, email, web) VALUES (nextval('persons_seq'), :first_name, :last_name, :company_name, :address, :city, :county, :state, :zip, :phone1, :phone2, :email, :web)")
        writer.setDataSource(dataSource)
        return writer
    }

    @Bean
    fun importPersonJob(): Job {
        return jobBuilderFactory!!
                .get("importPersonJob")
                .incrementer(RunIdIncrementer())
                .flow(step1())
                .end()
                .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!
                .get("step1")
                .chunk<Person, Person>(10)
                .reader(reader())
                .writer(writer())
                .build()
    }

}
