package com.hartron.eoffice.web.rest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hartron.eoffice.service.FileManagementService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * REST controller for managing Block.
 */
@RestController
@RequestMapping("/api")
public class FileManagementResource {

    private final FileManagementService fileManagementService;

    public FileManagementResource(FileManagementService fileManagementService) {
        this.fileManagementService = fileManagementService;
    }

    @RequestMapping(value = "/FileManagement", method = RequestMethod.POST)
    @ResponseBody
    public Object saveFile(@RequestParam(value = "filename") String filename, @RequestParam(value = "file") MultipartFile file,HttpServletRequest request) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String fileNameArray[] = file.getOriginalFilename().split("\\.");
            String fileextension="";
            if((fileNameArray.length>1))
            {
                fileextension=fileNameArray[(fileNameArray.length-1)].toString();
            }
            String inputFileName=filename;
            if(!(fileextension.equals("")))
            {
                inputFileName=inputFileName+"."+fileextension;
            }
            Path rootLocation = Paths.get("/var/uploads");
            try {
                Files.copy(file.getInputStream(), rootLocation.resolve(filename));
            } catch (Exception e) {
                throw new RuntimeException("FAIL!");
            }

        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

//    @RequestMapping(value = "/download", method = RequestMethod.GET)
//    @ResponseBody
//    public void download(@RequestParam (value="name") String name, final HttpServletRequest request, final HttpServletResponse response) {
//
//        String fileNameArray[]=name.split("\\.");
//
//        String filename=fileNameArray[0].toString();
//        String fileExtension="";
//        if((fileNameArray.length>1))
//        {
//            fileExtension=fileNameArray[1].toString();
//        }
//
//        ProjectServiceLogDTO projectServiceLogDTO= projectServiceLogService.findOne(filename);
//        String fileAction=projectServiceLogDTO.getActionTaken();
//
//        String outputFileName=fileAction;
//        if(!(fileExtension.equals("")))
//        {
//            outputFileName=outputFileName+"."+fileExtension;
//        }
//
//        File file = new File ("/var/uploads/" + name);
//        try (InputStream fileInputStream = new FileInputStream(file);
//             OutputStream output = response.getOutputStream();) {
//            response.reset();
//            response.setContentType("application/octet-stream");
//            response.setContentLength((int) (file.length()));
//            response.setHeader("Content-Disposition", "attachment; filename='"+outputFileName+"'");
//            IOUtils.copyLarge(fileInputStream, output);
//            output.flush();
//        } catch (IOException e) {
//        }
//    }

}
