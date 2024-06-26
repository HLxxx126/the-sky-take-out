package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO, HttpSession session) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

		//将登录数据员工id存储到session会话中
        //session.setAttribute("employee",employee.getId());
        //生成令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID,employee.getId());//将员工的id写入载荷
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .signWith(SignatureAlgorithm.HS256, jwtProperties.getAdminSecretKey())
//                .setExpiration(new Date(jwtProperties.getAdminTtl()))
//                .compact();
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims
        );

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "员工登出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新しい従業員を追加する
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新しい従業員を追加する")
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新しい従業員{}を追加する",employeeDTO);
        System.out.println("当前线程的id："+ Thread.currentThread().getId());
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * ページング検索
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("ページング検索")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("従業員のページング検索、パラメータは:{}",employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * ç
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("アカウントの有効化/無効化")
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("アカウント:{},{}の有効化/無効化",id,status);
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * Idに基づいて従業員の情報を検索する
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("Idに基づいて従業員の情報を検索する")
    public Result<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 従業員の情報を修正する
     * @param employeeDTO
     * @return
     */
    @PutMapping
    @ApiOperation("従業員の情報を修正する")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("従業員の情報を修正する：{}",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
