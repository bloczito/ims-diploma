package pl.wiktrans.ims.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.wiktrans.ims.model.Product;
import pl.wiktrans.ims.service.ProductsService;

import java.math.BigDecimal;

@Component
public class ProductsLoader implements ApplicationListener<ContextRefreshedEvent> {



    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }
}
