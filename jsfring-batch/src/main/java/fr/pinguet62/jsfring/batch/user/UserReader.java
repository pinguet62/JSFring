package fr.pinguet62.jsfring.batch.user;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class UserReader extends FlatFileItemReader<UserRow> {

    public UserReader() {
        setResource(new ClassPathResource("user.csv")); // TODO test
        setLineMapper(new DefaultLineMapper<UserRow>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "login", "password", "email" });
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<UserRow>() {
                    {
                        setTargetType(UserRow.class);
                    }
                });
            }
        });
    }

}