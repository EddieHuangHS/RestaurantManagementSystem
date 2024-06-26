package mhl.view;

import mhl.domain.*;
import mhl.service.BillService;
import mhl.service.DiningTableService;
import mhl.service.EmployeeService;
import mhl.service.MenuService;
import mhl.utils.Utility;

import java.util.List;

/**
 * 这是满汉楼的主界面
 */
public class MHLView {

    // 控制是否退出菜单
    private boolean loop = true;
    private String key = ""; // 接收用户输入
    // 定义EmployeeService 属性
    private EmployeeService employeeService = new EmployeeService();
    // 定义DiningTable的属性
    private DiningTableService diningTableService = new DiningTableService();
    // 定义 MenuService 属性
    private MenuService menuService = new MenuService();
    // 定义 BillService 属性
    private BillService billService = new BillService();

    public static void main(String[] args) {
        new MHLView().mainMenu();
    }

    // 显示所有餐桌状态
    public void listDiningTable(){
        System.out.println("\n餐桌编号\t\t餐桌状态");
        List<DiningTable> list = diningTableService.list();
        for (DiningTable diningTable : list) {
            System.out.println(diningTable);
        }
        System.out.println("=============== 显示完毕 ===============");
    }

    // 完成订座
    public void orderDiningTable(){
        System.out.println("=============== 预定餐桌 ===============");
        System.out.print("请选择要预定餐桌的编号（-1表示退出）：");
        int orderId = Utility.readInt();
        if(orderId == -1){
            System.out.println("=============== 取消预定餐桌 ===============");
            return;
        }
        // 该方法得到的是 Y 或者 N
        char key = Utility.readConfirmSelection();
        if(key == 'Y'){
            // 根据orderId 返回对应的 DiningTable对象，如果未null，说明该对象不存在
            DiningTable diningTable = diningTableService.getDiningTableById(orderId);
            if(diningTable == null){
                System.out.println("=============== 预定的餐桌不存在 ===============");
                return;
            }
            // 判断该餐桌的状态是否为"空"
            if(!("空".equals(diningTable.getState()))){
                System.out.println("=============== 该餐桌已经被预定或就餐中 ===============");
                return;
            }
            // 接收预定信息
            System.out.print("预定人的名字：");
            String orderName = Utility.readString(50);
            System.out.print("预定人的电话：");
            String orderTel = Utility.readString(50);
            // 更新餐桌状态
            if(diningTableService.orderDiningTable(orderId, orderName, orderTel)){
                System.out.println("=============== 预定餐桌成功 ===============");
            }else{
                System.out.println("=============== 预定餐桌失败 ===============");
            }
        }else{
            System.out.println("=============== 取消预定餐桌 ===============");
        }
    }

    // 显示所有菜品
    public void listMenu(){
        List<Menu> list = menuService.list();
        System.out.println("\n菜品编号\t\t菜品名\t\t类别\t\t价格");
        for (Menu menu : list) {
            System.out.println(menu);
        }
        System.out.println("=============== 所有菜品显示完毕 ===============");
    }

    // 完成点餐
    public void orderMenu(){
        System.out.println("=============== 点餐服务 ===============");
        System.out.print("请输入点餐的桌号（-1表示退出）");
        int orderDiningTableId = Utility.readInt();
        if(orderDiningTableId == -1){
            System.out.println("=============== 取消点餐 ===============");
            return;
        }
        System.out.print("请输入点餐的菜品号（-1表示退出）");
        int orderMenuId = Utility.readInt();
        if(orderMenuId == -1){
            System.out.println("=============== 取消点餐 ===============");
            return;
        }
        System.out.print("请输入点餐的菜品量（-1表示退出）");
        int orderNums = Utility.readInt();
        if(orderNums == -1){
            System.out.println("=============== 取消点餐 ===============");
            return;
        }

        // 验证餐桌号是否存在
        DiningTable diningTable = diningTableService.getDiningTableById(orderDiningTableId);
        if(diningTable == null){
            System.out.println("=============== 餐桌号不存在 ===============");
            return;
        }
        // 验证菜品编号
        Menu menu = menuService.getMenuById(orderMenuId);
        if(menu == null){
            System.out.println("=============== 菜品号不存在 ===============");
            return;
        }

        // 点餐
        if(billService.orderMenu(orderMenuId, orderNums, orderDiningTableId)){
            System.out.println("=============== 点餐成功 ===============");
        }else{
            System.out.println("=============== 点餐失败 ===============");
        }
    }

    // 显示账单信息
    public void listBill(){
//        List<Bill> allBills = billService.list();
//        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态");
//        for (Bill bill : allBills) {
//            System.out.println(bill);
//        }
//        System.out.println("=============== 显示完毕 ===============");
        List<MultiTableBean> multiTableBeans = billService.list2();
        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态\t\t菜品名\t\t价格");
        for (MultiTableBean bill : multiTableBeans) {
            System.out.println(bill);
        }
        System.out.println("=============== 显示完毕 ===============");
    }

    // 完成结账
    public void payBill(){
        System.out.println("=============== 结账服务 ===============");
        System.out.print("请选择要结账的餐桌编号（-1表示退出）：");
        int diningTableId = Utility.readInt();
        if(diningTableId == -1){
            System.out.println("=============== 取消结账 ===============");
            return;
        }
        // 验证餐桌是否存在
        DiningTable diningTable = diningTableService.getDiningTableById(diningTableId);
        if(diningTable == null){
            System.out.println("=============== 结账餐桌不存在 ===============");
            return;
        }
        // 验证餐桌是否有未结算的菜单
        if(!billService.hasPayBillByDiningTableId(diningTableId)){
            System.out.println("=============== 该餐位没有未结账账单 ===============");
            return;
        }

        System.out.print("结账方式（现金/支付宝/微信）回车表示退出：");
        String payMode = Utility.readString(20, ""); // 说明如果回车，就是返回 ""
        if("".equals(payMode)){
            System.out.println("=============== 取消结账 ===============");
            return;
        }

        char key = Utility.readConfirmSelection();
        if(key == 'Y'){
            if(billService.payBill(diningTableId, payMode)){
                System.out.println("=============== 完成结账 ===============");
            }else{
                System.out.println("=============== 结账失败 ===============");
            }
        }else{
            System.out.println("=============== 取消结账 ===============");
        }
    }

    // 显示主菜单
    public void mainMenu(){
        while(loop){
            System.out.println("=============== 满汉楼 ===============");
            System.out.println("\t\t 1 登录满汉楼");
            System.out.println("\t\t 2 退出满汉楼");
            System.out.print("请输入你的选择：");
            key = Utility.readString(1);
            switch(key){
                case "1":
                    System.out.print("请输入员工号：");
                    String empId = Utility.readString(50);
                    System.out.print("请输入密码：");
                    String pwd = Utility.readString(50);
                    Employee employee = employeeService.getEmployeeByIdAndPwd(empId, pwd);
                    // 到数据库去判断
                    if(employee != null){
                        System.out.println("=============== 登录成功 [" + employee.getName() + "] ===============");
                        // 显示二级菜单，这里二级菜单是循环操作，所以做成while
                        while(loop){
                            System.out.println("\n=============== 满汉楼二级菜单 ===============");
                            System.out.println("\t\t 1 显示餐桌状态");
                            System.out.println("\t\t 2 预定餐桌");
                            System.out.println("\t\t 3 显示所有菜名");
                            System.out.println("\t\t 4 点餐服务");
                            System.out.println("\t\t 5 查看账单");
                            System.out.println("\t\t 6 结账");
                            System.out.println("\t\t 9 退出满汉楼");
                            System.out.print("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key){
                                case "1":
                                    listDiningTable(); // 显示餐桌状态
                                    break;
                                case "2":
                                    orderDiningTable();
                                    break;
                                case "3":
                                    listMenu();
                                    break;
                                case "4":
                                    orderMenu();
                                    break;
                                case "5":
                                    listBill();
                                    break;
                                case "6":
                                    payBill();
                                    break;
                                case "9":
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("您的输入有误，请重新输入：");
                                    break;
                            }
                        }

                    }else{
                        System.out.println("=============== 登录失败 ===============");
                    }
                    break;
                case "2":
                    loop = false;
                    break;
                default:
                    System.out.println("您的输入有误，请重新输入：");
            }
        }
        System.out.println("您已退出满汉楼系统~");
    }
}
