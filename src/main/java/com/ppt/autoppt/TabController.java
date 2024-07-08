package com.ppt.autoppt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ppt.autoppt.Entity.bookEntity;
import com.ppt.autoppt.Entity.imageurlEntity;
import com.ppt.autoppt.service.bookservice;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFSheet;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.sl.usermodel.PictureData;




@Controller
public class TabController {
    
    @Autowired
    bookservice bookservice;

    @GetMapping("/")
    public String gettest(Model model) {

        List<bookEntity> bookList = bookservice.callbookAll();
        String[] names = new String[bookList.size()];
        int i = 0;

        for(bookEntity book:bookList){
            names[i] = book.bookname;

            i++;

        }

        model.addAttribute("names", names);

        return "tem.html";
    }

    @GetMapping("/ppttype")
    public String ppttype(Model model) {

        List<imageurlEntity> imageurlList = ;
        return "api/template.html";
    }
    

    @GetMapping("/remove")
    public String getMethodName() {
        bookservice.removebookAll();
        return "post/check.html";
    }  

    @GetMapping("/resend/test")
    public String fileResend(Model model) {
        String filepath = "C:/ppttest";
        File path = new File(filepath);
        String[] files = path.list();

        //XSSFWorkbook workbook = new XSSFWorkbook();
        //XSSFSheet sheet = workbook.createSheet("employee data");

        XMLSlideShow ppt = new XMLSlideShow();

        XSLFSlide silde1 = ppt.createSlide();

        XSLFTextBox textBox = silde1.createTextBox();
        XSLFTextParagraph paragraph = textBox.addNewTextParagraph();
        XSLFTextRun run = paragraph.addNewTextRun();
        
        run.setText("Not Unicode");
        run.setFontSize(30.0);
        textBox.setAnchor(new java.awt.Rectangle(100,100,400,100));
        
        try{
            FileInputStream pictureStream = new FileInputStream("C:/ppttest/testpic.png");
            byte[] pictureData = pictureStream.readAllBytes();
            PictureData pictureIndex = ppt.addPicture(pictureData, PictureData.PictureType.PNG);
            XSLFPictureShape picture = silde1.createPicture(pictureIndex);
            picture.setAnchor(new java.awt.Rectangle(100, 200, 200, 200));
            pictureStream.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        try(FileOutputStream out = new FileOutputStream("C:/ppttest/test1.pptx")){
            ppt.write(out);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Done");

        model.addAttribute("files", files);

        return "api/resendtest.html";
    }

    @GetMapping("read/test")
    public String readtest(Model model) throws IOException {

        FileInputStream inputStream = new FileInputStream("C:/ppttest/test1.pptx");
        XMLSlideShow ppt = new XMLSlideShow(inputStream);
        inputStream.close();

        java.util.List<XSLFSlide> slides = ppt.getSlides();

        BufferedImage img = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graph = img.createGraphics();

        //화질 상승
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graph.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        slides.get(0).draw(graph);

        FileOutputStream out = new FileOutputStream("C:/ppttest/silde1.png");
        ImageIO.write(img, "png", out);
        out.close();
        
        System.out.println("done");


        return "api/resendtest.html";
    }
    

    /*  //Model의 다른 사용법.
    @RequestMapping("/resend/test")
    public ModelAndView fileResend() {
        ModelAndView mv = new ModelAndView();
        File path = new File("C:/ppttest");
        String[] files = path.list();
        System.out.println(files);

        mv.addObject("files", files);
        mv.setViewName("api/resendtest");
        return mv;
    }
    */
    
    @PostMapping("/reqbook")
    public String postMethodName(bookEntity book) {
        bookservice.savebook(book.bookname);

        return "post/check.html";
    }

    @RequestMapping("/download/{file}")
    public void download(@PathVariable String file, HttpServletResponse res) throws IOException {

        File f = new File("C:/ppttest/", file);

        res.setContentType("application/download");
        res.setContentLength((int)f.length());
        res.setHeader("Content-disposition", "attachment;filename=\"" + file + "\"");

        OutputStream os = res.getOutputStream();

        FileInputStream files = new FileInputStream(f);
        FileCopyUtils.copy(files, os);
        files.close();
        os.close();
    }
    

}
