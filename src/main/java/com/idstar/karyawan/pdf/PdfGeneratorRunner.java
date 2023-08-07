package com.idstar.karyawan.pdf;

import com.idstar.karyawan.configuration.Config;
import com.idstar.karyawan.repository.oauth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PdfGeneratorRunner
//        implements CommandLineRunner
{

    @Autowired
    private PdfGenerateService pdfGenerateService;

    @Autowired
    UserRepository userRepository;


    Config config = new Config();

//    @Override
//    public void run(String... args) throws Exception {
//        Map<String, Object> data = new HashMap<>();
//        Customer customer = new Customer();
//        customer.setCompanyName("Simple Solution");
//        customer.setContactName("John Doe");
//        customer.setAddress("123, Simple Street");
//        customer.setEmail("john@simplesolution.dev");
//        customer.setPhone("123 456 789");
//        data.put("customer", customer);
//
//        List<QuoteItem> quoteItems = new ArrayList<>();
//        QuoteItem quoteItem1 = new QuoteItem();
//        quoteItem1.setDescription("Test Quote Item 1,Test Quote Item 1,Test Quote Item 1,Test Quote Item 1,Test Quote Item 1, Test Quote Item 1 Test Quote Item 1 Test Quote Item 1 Test Quote Item 1 Test Quote Item 1");
//        quoteItem1.setQuantity(1);
//        quoteItem1.setUnitPrice(100.0);
//        quoteItem1.setTotal(100.0);
//        quoteItems.add(quoteItem1);
//
//        QuoteItem quoteItem2 = new QuoteItem();
//        quoteItem2.setDescription("Test Quote Item 2");
//        quoteItem2.setQuantity(4);
//        quoteItem2.setUnitPrice(500.0);
//        quoteItem2.setTotal(2000.0);
//        quoteItems.add(quoteItem2);
//
//        QuoteItem quoteItem3 = new QuoteItem();
//        quoteItem3.setDescription("Test Quote Item 3");
//        quoteItem3.setQuantity(2);
//        quoteItem3.setUnitPrice(200.0);
//        quoteItem3.setTotal(400.0);
//        quoteItems.add(quoteItem3);
//
//        data.put("quoteItems", quoteItems);
//        System.out.println("masuk pDF");
//        pdfGenerateService.generatePdfFile("quotation", data, "quotation.pdf");
//        System.out.println("masuk pDF 00");
//    }

    //            @Override
//    public void run(String... args) throws Exception {

}
