package org.example.firstinstance.controller.firstinstanceurl;
import lombok.RequiredArgsConstructor;
import org.example.config.auth.LoginUser;
import org.example.config.auth.dto.SessionUser;
import org.example.domain.user.User;
import org.example.domain.user.UserService;
import org.example.domain.useruploadgradeadmin.UserUploadGradeAdmin;
import org.example.domain.useruploadgradeadmin.UserUploadGradeAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class InstanceUrlController {

    private final UserUploadGradeAdminService userUploadGradeAdminService;
    private final UserService userService;

    @GetMapping("/administer/instanceurl")
    public String index(Model model, @LoginUser SessionUser user){
        if (user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("Role", user.getRole());
        }
        //firstInstance index의 처음 위치.
        return "firstinstance/index";
    }

    // 개발중, 테스트용 url연결 만듬.
    @GetMapping("/")
    public String index2(){


        return "redirect:/administer/instanceurl";
    }

    @GetMapping("/changeRole")
    public String changeRole(Model model, @LoginUser SessionUser user){
        if (user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("Role", user.getRole());
        }
        if (model.getAttribute("sevMessage")!=null){

        }else{
            model.addAttribute("sevMessage", null);
        }

        UserUploadGradeAdmin userUploadGradeAdmin = userUploadGradeAdminService.findByEmail(user.getEmail());
        if(userUploadGradeAdmin.getCertNum()!=null && userUploadGradeAdmin.getIsPermit().equals("N")){
            System.out.println("true--- again, num compare");
            model.addAttribute("sevMessage2", "아직 허락을 기다리는 중입니다. 잘못 올렸을 경우, 다시 업로드 가능합니다.");
        }

        return "firstinstance/changeRole";
    }

    @PostMapping("/changeRole")
    public String changeRole2(Model model, @LoginUser SessionUser user,
                              @RequestParam("attachFile") MultipartFile file,
                              @RequestParam("qualificationNumber") String number,
                              @RequestParam("roleSelect") String select
    ){
        if (user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("Role", user.getRole());
        }
        if(select.equals("USER")){
            if(("USER").equals(user.getRole())){
                return "firstinstance/changeRole";
            }else{

            }
        }else if(select.equals("ADMIN")){

            try{
                if(!file.isEmpty()){
                    String uploadDir = "C:\\category\\fileupload";
                    String fileName = file.getOriginalFilename();
                    String uuidName = UUID.randomUUID().toString();
                    String filePath = Paths.get(uploadDir, uuidName).toString();

                    Files.createDirectories(Paths.get(uploadDir));
                    Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

                    System.out.println("file path,");
                    System.out.println(filePath);
                    System.out.println("origin file name, ");
                    System.out.println(fileName);

                    User nowUser = userService.findByEmail(user.getEmail());

                    userUploadGradeAdminService.joinFile(nowUser,fileName,number, filePath);

                    UserUploadGradeAdmin userUploadGradeAdmin = userUploadGradeAdminService.findByEmail(user.getEmail());
                    if(userUploadGradeAdmin.getCertNum()!=null && userUploadGradeAdmin.getIsPermit().equals("N")){

                        model.addAttribute("sevMessage2", "아직 허락을 기다리는 중입니다. 잘못 올렸을 경우, 다시 업로드 가능합니다.");
                    }

                }else{
                    model.addAttribute("sevMessage", "파일이 누락되었습니다. 다시 입력해주세요.");

                    UserUploadGradeAdmin userUploadGradeAdmin = userUploadGradeAdminService.findByEmail(user.getEmail());
                    if(userUploadGradeAdmin.getCertNum()!=null && userUploadGradeAdmin.getIsPermit().equals("N")){

                        model.addAttribute("sevMessage2", "아직 허락을 기다리는 중입니다. 잘못 올렸을 경우, 다시 업로드 가능합니다.");
                    }

                    return "firstinstance/changeRole";
                }
            }catch(Exception e){
                e.printStackTrace();
                model.addAttribute("sevMessage", "파일이 누락되었습니다. 다시 입력해주세요.");
                return "firstinstance/changeRole";
            }
        }

        return "firstinstance/changeRole";
    }

    @GetMapping("/cus-login")
    public String index3(){
        //firstInstance index의 처음 위치.
        return "firstinstance/index";
    }
}