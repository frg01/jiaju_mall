package com.hspedu.furns.web;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import com.hspedu.furns.utils.DataUtils;
import com.hspedu.furns.utils.WebUtils;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class FurnServlet extends BasicServlet {
    //定义一个FurnService属性
    private FurnService furnService = new FurnServiceImpl();

    /**
     * 模板设计，反射，动态绑定来调用list方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FurnServlet 的list方法被调用。。");
        List<Furn> furns = furnService.queryFurns();
//        for (Furn furn : furns) {
//            System.out.println(furn);
//        }
        //把拿到的集合放入到request域
        request.setAttribute("furns", furns);
        //请求转发
        request.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(request, response);
    }

    //添加家居
    public void addFurn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Furn furn = new Furn();
//        try {
//            String name = request.getParameter("name");
//            String maker = request.getParameter("maker");
//            String price = request.getParameter("price");
//            Integer sales = Integer.parseInt(request.getParameter("sales"));
//            Integer stock = Integer.parseInt(request.getParameter("stock"));
//            //暂时有两种方法防止数据格式错误，正则表达式或者try-catch
//            Furn furn = new Furn(null, name, maker, new BigDecimal(price), sales, stock, "assets/images/product-image/5.jpg");
        //第二种方法 利用反射原理，使用BeanUtils的jar方法 对表单参数进行封装
//            try {
//                BeanUtils.populate(furn, request.getParameterMap());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } catch (NumberFormatException e) {
//            request.setAttribute("mesg", "有数据输入的格式不正确！");
//            request.getRequestDispatcher("/views/manage/furn_add.jsp").forward(request, response);
//        }
//        System.out.println("BeanUtils封装后=" + furn);
        //封装上述代码到DataUtils，调用其方法
        Furn furn = DataUtils.copyParamToBean(request.getParameterMap(), new Furn());
        int i = furnService.addFurn(furn);
        if (i >= 1) {
            System.out.println("添加家居成功。。.");
            //使用请求重定向  请求转发不会改变浏览器地址，刷新会依然执行原来的浏览器地址，造成数据重复提交
//            request.getRequestDispatcher("/manage/furnServlet?action=list").forward(request,response);
            //让浏览器重新请求，这里回送url 是一个完整的url
            response.sendRedirect(request.getContextPath() + "/manage/furnServlet?action=page&pageNum=" + request.getParameter("pageNum"));
        } else {
            System.out.println("添加家居失败。。.");
            request.getRequestDispatcher("/views/manage/furn_add.jsp").forward(request, response);
        }
    }


    //删除家居
    protected void deleteFurn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //防止接收的id不是一个字符串，可以转为数字就转否则返回一个默认值
        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        int i = furnService.deleteFurnById(id);
        response.sendRedirect(request.getContextPath() + "/manage/furnServlet?action=page&pageNum=" + request.getParameter("pageNum"));
//        if (i >= 1){
//            System.out.println("删除成功");
//            HttpSession session = request.getSession();
//            session.setAttribute("mesg","删除成功");
//            response.sendRedirect(request.getContextPath() + "/manage/furnServlet?action=list");
//        }else{
//            System.out.println("删除失败");
//            HttpSession session = request.getSession();
//            session.setAttribute("mesg","删除失败");
//            response.sendRedirect(request.getContextPath() + "/manage/furnServlet?action=list");
//        }
    }


    //通过id查询某个家居
    protected void queryFurnById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        System.out.println("1 id" + idStr);
        System.out.println("2 num" + request.getParameter("pageNum"));
        int id = DataUtils.parseInt(idStr, 0);
        Furn furn = furnService.queryFurnById(id);
        //将furn放入request中
        request.setAttribute("furn", furn);
        //将pageNum保存到request，请求带来的参数pageNum，如果再次请求转发到下一个页面，则参数依然存在param.pageNum
        //或者拿出来放入attribute
        request.setAttribute("pageNum", request.getParameter("pageNum"));
//        request.setAttribute("id",furn.getId());
//        request.setAttribute("name",furn.getName());
//        request.setAttribute("maker",furn.getMaker());
//        request.setAttribute("price",furn.getPrice());
//        request.setAttribute("sales",furn.getSales());
//        request.setAttribute("stock",furn.getStock());
//        request.setAttribute("stock",furn.getStock());
//        request.setAttribute("imgPath",furn.getImgPath());

        request.getRequestDispatcher("/views/manage/furn_update.jsp").forward(request, response);
    }

    //修改家居信息
//    protected void updateFurn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Furn furn = DataUtils.copyParamToBean(request.getParameterMap(), new Furn());
//        //考虑分页，拿出pageNum，
//        String pageNum = request.getParameter("pageNum");
//        System.out.println("修改页面的pageNum" + pageNum);
//        int i = furnService.updateFurn(furn);
//        if (i >= 1){
//            System.out.println("修改成功");
//            response.sendRedirect(request.getContextPath() +"/manage/furnServlet?action=page&pageNum=" + pageNum);
//        }else{
//            System.out.println("修改失败");
//            response.sendRedirect(request.getContextPath() +"/manage/furnServlet?action=page&pageNum=" + pageNum);
//        }
//    }

    //修改家居信息
    protected void updateFurn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        //获取到对应的furn对象
        Furn furn = furnService.queryFurnById(id);
        if (furn == null) {//为空就不处理
            return;
        }
        //获取图片路径，更换新图片就删除原来的图片
        String imgPath = furn.getImgPath();

        //1. 判断是不是文件表单(enctype="multipart/form-data")
        if (ServletFileUpload.isMultipartContent(request)) {
            //2. 创建 DiskFileItemFactory 对象, 用于构建一个解析上传数据的工具对象
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            //3. 创建一个解析上传数据的工具对象
            ServletFileUpload servletFileUpload =
                    new ServletFileUpload(diskFileItemFactory);
            //解决接收到文件名是中文乱码问题
            servletFileUpload.setHeaderEncoding("utf-8");

            //4. 关键的地方, servletFileUpload 对象可以把表单提交的数据text / 文件
            //   将其封装到 FileItem 文件项中
            try {
                List<FileItem> list = servletFileUpload.parseRequest(request);
                //遍历，并分别处理
                for (FileItem fileItem : list) {
                    //判断是不是一个文件
                    if (fileItem.isFormField()) {//如果是true就是文本 input text

                        if ("name".equals(fileItem.getFieldName())) {//家居名
                            furn.setName(fileItem.getString("utf-8"));
                        } else if ("maker".equals(fileItem.getFieldName())) {//制造商
                            furn.setMaker(fileItem.getString("utf-8"));
                        } else if ("price".equals(fileItem.getFieldName())) {//价格
                            furn.setPrice(new BigDecimal(fileItem.getString()));
                        } else if ("sales".equals(fileItem.getFieldName())) {//销量
                            furn.setSales(new Integer(fileItem.getString()));
                        } else if ("stock".equals(fileItem.getFieldName())) {//库存
                            furn.setStock(new Integer(fileItem.getString()));
                        }
                    } else {//是一个文件

                        //获取上传的文件的名字
                        String name = fileItem.getName();
                        //如果用户选择新的图片，name != ""  没有选择新图片则不用进入
                        if (!"".equals(name)) {
                            //把这个上传到 服务器的 temp下的文件保存到你指定的目录
                            //1.指定一个目录 , 就是我们网站工作目录下
                            String filePath = WebUtils.FURN_IMG_DIRECTORY;
                            //2. 获取到完整目录 [io/servlet基础]
                            String fileRealPath =
                                    request.getServletContext().getRealPath(filePath);

                            //3. 创建这个上传的目录=> 创建目录?=> Java基础
                            //   思路; 我们也一个工具类，可以返回 /2024/11/11 字符串
                            File fileRealPathDirectory = new File(fileRealPath + "/" + WebUtils.getYearMonthDay());
                            if (!fileRealPathDirectory.exists()) {//不存在，就创建
                                fileRealPathDirectory.mkdirs();//创建
                            }

                            //4. 将文件拷贝到fileRealPathDirectory目录
                            //   构建一个上传文件的完整路径 ：目录+文件名
                            //   对上传的文件名进行处理, 前面增加一个前缀，保证是唯一即可, 不错
                            name = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + name;
                            String fileFullPath = fileRealPathDirectory + "/" + name;
                            fileItem.write(new File(fileFullPath));//保存

                            fileItem.getOutputStream().close();
                            //更新家居图片路径
                            furn.setImgPath(filePath + "/" + WebUtils.getYearMonthDay() + name);

                            //删除原来的旧图片  拿到真路径来删除
                            String realImgPath =
                                    request.getServletContext().getRealPath(imgPath);

                            File file = new File(realImgPath);
                            if (file.exists() && file.isFile()){
                                System.out.println("删除成功");
                                file.delete();
                            }

                            System.out.println("删除后是否存在" + file.exists());
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("不是文件表单...");
        }

        //考虑分页，拿出pageNum，
        int pageNum = DataUtils.parseInt(request.getParameter("pageNum"), 1);
        System.out.println("修改页面的pageNum" + pageNum);
        furnService.updateFurn(furn);

        request.getRequestDispatcher("/views/manage/update_ok.jsp").forward(request, response);


    }

    //显示当前页面
    protected void page(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        int pageNum = DataUtils.parseInt(request.getParameter("pageNum"), 1);
        int pageSize = DataUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        Page page = furnService.page(pageNum, pageSize);
        //设置url
        page.setUrl("manage/furnServlet?action=page&pageNum=");
        request.setAttribute("page", page);
        request.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(request, response);
    }
}
