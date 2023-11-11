package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //后端需要进行md5加密，然后再进行比对
        String MD5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!MD5Password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /*新增员工*/
    public void save(EmployeeDTO employeeDTO) {
        System.out.println("线程(save)ID："+Thread.currentThread().getName());

        //对象属性拷贝
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);

        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //TODO:将来改为当前登录管理者

        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert(employee);
    }

    /*员工分页查询*/
    public PageResult page(EmployeePageQueryDTO pageQueryDTO){
        IPage<Employee> iPage = new Page<>(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        employeeMapper.selectPageByName(iPage,pageQueryDTO.getName());

        System.out.println(iPage.getRecords());
        PageResult result = new PageResult();
        result.setTotal(iPage.getTotal());
        result.setRecords(iPage.getRecords());

        return result;
    }

    /*启用禁用员工权限*/
    public Result startOrStop(Integer status, Long id) {
        Employee employee = new Employee();
        employee.setStatus(status);
        employee.setId(id);
        employeeMapper.updateById(employee);
        return Result.success(null);
    }

    /*根据id查询员工*/
    public Result getEmpInfo(Long id) {
        Employee employee = employeeMapper.selectById(id);
        //返回前端的密码加密保证安全
        employee.setPassword("******");
        return Result.success(employee);
    }

    //编辑员工信息
    @AutoFill(value = OperationType.UPDATE)
    public Result updateEmp(Employee employee) {
        employeeMapper.updateById(employee);
        return Result.success();
    }
}
