package com.example.web;

import com.lowagie.text.pdf.BaseFont;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;


@Controller
public class PdfController {

  @Autowired
  public SpringTemplateEngine templateEngine;

  @ResponseBody
  @RequestMapping(value = "/path")
  public String showPath() throws IOException {
    Context context = new Context(LocaleContextHolder.getLocale());
    String htmlBody = templateEngine.process("pdf/style.html", context);
    System.out.println(htmlBody);

    File dest = Paths.get("20240223.pdf").toFile();

    OutputStream os = new FileOutputStream(dest);
    ITextRenderer renderer = new ITextRenderer();
    ITextFontResolver fontResolver = renderer.getFontResolver();
    final ClassPathResource resource = new ClassPathResource("fonts/arial.ttf");
    fontResolver.addFont(resource.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

    renderer.setDocumentFromString(htmlBody);
    renderer.layout();
    renderer.createPDF(os);
    return "ok";
  }

}
