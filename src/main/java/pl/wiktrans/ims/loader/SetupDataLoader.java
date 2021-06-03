package pl.wiktrans.ims.loader;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.wiktrans.ims.model.*;
import pl.wiktrans.ims.repository.*;
import pl.wiktrans.ims.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired private RoleRepository roleRepository;

    @Autowired private PrivilegeRepository privilegeRepository;

    @Autowired private UserRepository userRepository;

    @Autowired private CompanyRepository companyRepository;

    @Autowired private OrderRepository orderRepository;

    @Autowired private CustomerRepository customerRepository;

    @Autowired private ProductsService productsService;

    @Autowired private OrderElementService orderElementService;

    @Autowired private MerchOrderService merchOrderService;

    @Autowired private ShipmentService shipmentService;

    @Autowired private ShipmentElementService shipmentElementService;

    @Autowired private CustomerObjectService customerObjectService;

    @Autowired private ProductPriceService productPriceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private boolean alreadySetup = false;




    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (alreadySetup) return;



        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);

        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Collections.singletonList(readPrivilege));

        Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        User user = new User();
        user.setUsername("test");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setRoles(Arrays.asList(adminRole.get(), userRole.get()));
//        user.setRoles(Collections.singleton(userRole.get()));
        user.setEnabled(true);
        userRepository.save(user);

        Company company = new Company();
        company.setName("Firma 1");
        company.setEmail("asd@asd.asd");
        companyRepository.save(company);

        Customer customer = new Customer();
        customer.setName("Klient 1");
        customer.setEmail("klient1@asd.pl");
        customer.setNip("1234567890");
        customer.setPhone("147852369");
        customerRepository.save(customer);



        Order order = new Order();
        order.setOrderNumber("2020/04 BKR");
        order.setCompany(company);
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customer);
        order.setDeadline(LocalDate.of(2022, 5, 15));
        orderRepository.save(order);

//        for (int i = 0; i < 30; ++i) {
//            Order order1 = new Order();
//            order1.setOrderNumber("2020/" + i%12 + " BKR" + i);
//            order1.setCompany(company);
//            order1.setOrderDate(LocalDate.now());
//            order1.setCustomer(customer);
//            order1.setDeadline(LocalDate.of(2022, i % 12 + 1, (i % 25) + 1));
//            orderRepository.save(order1);
//        }

        company.setOrders(Collections.singletonList(order));
        companyRepository.save(company);

        loadProductsData();
        List<MerchOrder> merchOrders = loadMerchOrders(order);

        Random random = new Random();
        merchOrders.stream()
                .map(MerchOrder::getOrderElements)
                .flatMap(List::stream)
                .map(OrderElement::getProduct)
                .collect(Collectors.toSet())
                .forEach(product -> {
            ProductPrice productPrice = new ProductPrice();
            productPrice.setOrder(order);
            productPrice.setPrice(BigDecimal.valueOf(random.nextInt(200) + 50));
            productPrice.setProduct(product);
            productPriceService.save(productPrice);
        });


        Address objectAddress = new Address();
        objectAddress.setCity("Berlin");
        objectAddress.setCountry("Germany");
        objectAddress.setStreet("Adolf Hitler Strasse");
        objectAddress.setHouseNumber("2137");
        objectAddress.setVoivodeship("Monachium");

        CustomerObject customerObject = new CustomerObject();
        customerObject.setCustomer(customer);
        customerObject.setContactName("Helmut");
        customerObject.setContactSurname("Szwab");
        customerObject.setContactTitle("Mr.");
        customerObject.setAddress(objectAddress);
        customerObjectService.save(customerObject);

        Shipment shipment = new Shipment();
        shipment.setOrder(order);
        shipment.setComment("Testowy");
        shipment.setShipmentDate(LocalDate.now());
        shipment.setShipmentObject(customerObject);
        shipmentService.save(shipment);


        Optional<MerchOrder> optionalMerchOrder = merchOrderService.getAll().stream().findFirst();
        optionalMerchOrder.ifPresent(merchOrder -> {
            OrderElement orderElement = merchOrder.getOrderElements().get(0);

            ShipmentElement shipmentElement = new ShipmentElement();
            shipmentElement.setShipment(shipment);
            shipmentElement.setProduct(orderElement.getProduct());
            shipmentElement.setQuantity(orderElement.getQuantity()/2);
            shipmentElementService.save(shipmentElement);

        });






        alreadySetup = true;
    }


    Privilege createPrivilegeIfNotFound(String name) {
        Optional<Privilege> optionalPrivilege = privilegeRepository.findByName(name);
        return optionalPrivilege.orElseGet(() -> {
            Privilege privilege = new Privilege(name);
            privilegeRepository.save(privilege);
            return privilege;
        });
    }


    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        return optionalRole.orElseGet(() -> {
            Role role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
            return role;
        });
    }


    public void loadProductsData() {
        Product product = new Product();
        product.setCode(123456L);
        product.setName("Łupanka 180");
        product.setHeight(180D);
        product.setWidth(50d);
        product.setBasePrice(new BigDecimal(156));

        Product product1 = new Product();
        product1.setCode(78962L);
        product1.setName("Łupanka 90");
        product1.setHeight(90D);
        product1.setWidth(30d);
        product1.setBasePrice(new BigDecimal(126));

        Product product2 = new Product();
        product2.setCode(14785L);
        product2.setName("Płot 180");
        product2.setHeight(180D);
        product2.setWidth(180d);
        product2.setBasePrice(new BigDecimal(290));

        Product product3 = new Product();
        product3.setCode(123456L);
        product3.setName("Karmik brzoza");
        product3.setHeight(30D);
        product3.setWidth(40d);
        product3.setBasePrice(new BigDecimal(100));

        Product product4 = new Product();
        product4.setCode(963586L);
        product4.setName("Karmnik drewno");
        product4.setHeight(20D);
        product4.setWidth(20d);
        product4.setBasePrice(new BigDecimal(63));

        Product product5 = new Product();
        product5.setCode(1297236L);
        product5.setName("Łopanka mini");
        product5.setHeight(30D);
        product5.setWidth(10d);
        product5.setBasePrice(new BigDecimal(60));

        Product product6 = new Product();
        product6.setCode(123456L);
        product6.setName("Hochbet");
        product6.setHeight(40D);
        product6.setWidth(120d);
        product6.setBasePrice(new BigDecimal(80));

        productsService.save(product);
        productsService.save(product1);
        productsService.save(product2);
        productsService.save(product3);
        productsService.save(product5);
        productsService.save(product6);
    }

    public List<MerchOrder> loadMerchOrders(Order order) {
        Random random = new Random();

        List<Product> allProducts = productsService.getAll();

        MerchOrder merchOrder = new MerchOrder();
        merchOrder.setOrder(order);
        merchOrder.setComment("lorem ipsum dolor sit amet consectetur adipiscing elit");
        merchOrder.setMerchOrderDate(LocalDateTime.now());
        merchOrder.setOrderElements(new ArrayList<>());
        merchOrderService.save(merchOrder);



        allProducts.forEach(product -> {
            OrderElement orderElement = new OrderElement();
            orderElement.setProduct(product);
            orderElement.setQuantity(random.nextInt(100));
            orderElement.setMerchOrder(merchOrder);

            orderElementService.save(orderElement);
            merchOrder.getOrderElements().add(orderElement);
        });


        MerchOrder merchOrder2 = new MerchOrder();
        merchOrder2.setOrder(order);
        merchOrder2.setComment("DUPA DUPA DUPA");
        merchOrder2.setMerchOrderDate(LocalDateTime.now());
        merchOrder2.setOrderElements(new ArrayList<>());
        merchOrderService.save(merchOrder2);

        List<Product> subProducts = allProducts.subList(0, (int) (allProducts.size() / 2));
        Collections.shuffle(subProducts);

        subProducts.forEach(product -> {
            OrderElement orderElement = new OrderElement();
            orderElement.setProduct(product);
            orderElement.setQuantity(random.nextInt(100));
            orderElement.setMerchOrder(merchOrder2);


            orderElementService.save(orderElement);
            merchOrder2.getOrderElements().add(orderElement);
        });

        return Arrays.asList(merchOrder, merchOrder2);
    }



}
