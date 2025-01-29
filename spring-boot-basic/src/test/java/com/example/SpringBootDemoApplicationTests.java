package com.example;

import com.example.utils.Html2Pdf;
import com.lowagie.text.pdf.BaseFont;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

@SpringBootTest
public class SpringBootDemoApplicationTests {

  @Autowired
  public SpringTemplateEngine templateEngine;

  @Test
  public void generatePdfWithCssStyle1() throws IOException {
    Context context = new Context(LocaleContextHolder.getLocale());
    String htmlBody = templateEngine.process("pdf/style.html", context);
    System.out.println(htmlBody);

    File dest = Paths.get("css-1.pdf").toFile();

    OutputStream os = new FileOutputStream(dest);
    ITextRenderer renderer = new ITextRenderer();

    ITextFontResolver fontResolver = renderer.getFontResolver();
    final ClassPathResource resource = new ClassPathResource("fonts/arial.ttf");
    fontResolver.addFont(resource.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

    renderer.setDocumentFromString(htmlBody);
    renderer.layout();
    renderer.createPDF(os);

    FileOutputStream outputStream = new FileOutputStream("css-2.pdf");
    Html2Pdf.generator(outputStream, htmlBody);
  }


}
