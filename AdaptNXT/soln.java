import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.opencsv.CSVWriter;

public class ProductScraper {
    public static void main(String[] args) throws IOException {
        // Load the web page
        String url = "https://www.walmart.com/browse/electronics/dell-gaming-laptops/3944_3951_7052607_1849032_4519159";
        Document doc = Jsoup.connect(url).get();
       
        // Get the product details
        ArrayList<String[]> productDetails = new ArrayList<String[]>();
        Elements products = doc.select("div.search-result-gridview-item-wrapper");
        for (Element product : products) {
            String name = product.select("a.product-title-link").text();
            String price = product.select("span.price").text();
            String sku = product.select("div.search-result-product-id").text();
            String model = product.select("div.search-result-product-info-item:nth-child(1)").text();
            String category = product.select("ol.breadcrumb-list li:last-child").text();
            String description = product.select("div.search-result-product-description").text();
            String[] details = {name, price, sku, model, category, description};
            productDetails.add(details);
        }
       
        // Write the data to a CSV file
        String[] header = {"Product Name", "Product Price", "Item Number/SKU/Product Code", "Model Number", "Product Category", "Product Description"};
        String fileName = "product_details.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(fileName));
        writer.writeNext(header);
        writer.writeAll(productDetails);
        writer.close();
       
        System.out.println("Product details exported to " + fileName);
    }
}
