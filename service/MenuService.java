package mhl.service;

import mhl.dao.MenuDAO;
import mhl.domain.Menu;

import java.util.List;

/**
 * 完成对 Menu 表的各种调用 (通过调用 MenuDAO)
 */
public class MenuService {

    // 定义 MenuDAO 属性
    private MenuDAO menuDAO = new MenuDAO();

    // 返回所有的菜品，返回给界面使用
    public List<Menu> list(){
        return menuDAO.queryMulti("select * from menu", Menu.class);
    }

    // 根据id返回Menu对象
    public Menu getMenuById(int id){
        return menuDAO.querySingle("select * from menu where id = ?", Menu.class, id);
    }
}
