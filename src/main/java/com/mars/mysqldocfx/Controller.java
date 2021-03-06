package com.mars.mysqldocfx;

import com.jfoenix.controls.*;
import com.mars.mysqldocfx.common.CommonConstant;
import com.mars.mysqldocfx.service.DatabaseInfo;
import com.mars.mysqldocfx.service.FileSave;
import com.mars.mysqldocfx.service.MarkDownBuild;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class Controller {
    @FXML
    private TextField host;
    @FXML
    private TextField user;
    @FXML
    private TextField db;
    @FXML
    private JFXPasswordField password;
    @FXML
    private ToggleButton ssl;
    @FXML
    private JFXDialog dialog;
    @FXML
    private StackPane root;
    @FXML
    private Label exportFilePath;
    @FXML
    private Label exportFilePathLabel;

    @FXML
    public void generate(ActionEvent actionEvent) {
        String hostText = host.getText();
        if(host == null || StringUtils.isBlank(hostText)){
            alertDialog("数据库地址不能为空");
            return;
        }
        String userText = user.getText();
        if(user == null || StringUtils.isBlank(userText)){
            alertDialog("数据库用户不能为空");
            return;
        }
        String dbText = db.getText();
        if(db == null || StringUtils.isBlank(dbText)){
            alertDialog("数据库名称不能为空");
            return;
        }
        String passwordText = password.getText();
        if(password == null || StringUtils.isBlank(passwordText)){
            alertDialog("数据库密码不能为空");
            return;
        }
        boolean selected = ssl.isSelected();
        updateConfigFile(hostText, userText, dbText, passwordText, selected);
        try {
            DatabaseInfo databaseInfo=new DatabaseInfo(hostText,userText,passwordText,dbText, String.valueOf(selected));
            HashMap tablelistsInfo= null;

            tablelistsInfo = databaseInfo.getDatabaseAllInfo();
            MarkDownBuild markDownBuild=new MarkDownBuild();
            //解析markdown成markdown
            String text= markDownBuild.buildMarkdown(tablelistsInfo);
            //保存markdown 文件
            FileSave.saveMarkdown(exportFilePath.getText(), dbText,text);
            //保存html 文件
            FileSave.saveHtml(exportFilePath.getText(), dbText,text);
            successDialog("生成成功");
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updateConfigFile(String hostText, String userText, String dbText, String passwordText, boolean selected){
        File file = getConfigFile();
        Properties prop = new Properties();
        try {
            if(!file.exists()){
                FileWriter mFile = new FileWriter(file.getPath());
                mFile.write("");
                mFile.close();
            }
            InputStream in = new FileInputStream(file.getPath());
            //从输入流中读取属性列表（键和元素对）
            prop.load(in);
            //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
            //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream out = new FileOutputStream(file.getPath());
            prop.setProperty(CommonConstant.HOST, hostText);
            prop.setProperty(CommonConstant.DB, dbText);
            prop.setProperty(CommonConstant.USER, userText);
            prop.setProperty(CommonConstant.PASSWORD, passwordText);
            prop.setProperty(CommonConstant.SSL, String.valueOf(selected));
            prop.setProperty(CommonConstant.EXPORT_FILE_PATH, exportFilePath.getText());
            prop.store(out,"update");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void alertDialog(String content) {
        showDialog("警告", content);
    }
    private void successDialog(String content) {
        showDialog("提示", content);
    }

    private void showDialog(String title, String content) {
        JFXAlert alert = new JFXAlert((Stage) dialog.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));
        layout.setBody(new Label(content));
        JFXButton closeButton = new JFXButton("确定");
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }

    public void init() {
        File file = getConfigFile();
        if(file.exists()){
            try {
                InputStream inStream = new FileInputStream(file);
                Properties prop = new Properties();
                prop.load(inStream);
                String hostConfText = prop.getProperty(CommonConstant.HOST);
                String dbConfText = prop.getProperty(CommonConstant.DB);
                String userConfText = prop.getProperty(CommonConstant.USER);
                String passwordConfText = prop.getProperty(CommonConstant.PASSWORD);
                String sslConfText = prop.getProperty(CommonConstant.SSL);
                String exportFilePathConfText = prop.getProperty(CommonConstant.EXPORT_FILE_PATH);
                this.exportFilePath.setText(exportFilePathConfText);
                host.setText(hostConfText);
                db.setText(dbConfText);
                user.setText(userConfText);
                password.setText(passwordConfText);
                boolean select = StringUtils.isBlank(sslConfText) || "true".equals(sslConfText);
                ssl.setSelected(select);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ClassLoader classLoader = getClass().getClassLoader();
        ImageView image = new ImageView(new Image(classLoader.getResourceAsStream("images/wenjianjia_1.png")));
        image.setPreserveRatio(true);
        image.setFitHeight(28);
        image.setFitHeight(28);
        exportFilePathLabel.setGraphic(image);
        Tooltip tooltip = new Tooltip("选择保存地址");
        exportFilePathLabel.setTooltip(tooltip);
    }

    private File getConfigFile() {
        String appdataPath = System.getenv(CommonConstant.APPDATA);
        String filePath = appdataPath + System.getProperty("file.separator") + CommonConstant.PROJECT_DIR_NAME;
        FileSave.mkdirIfNotExist(filePath);
        return FileUtils.getFile(filePath + System.getProperty("file.separator") + CommonConstant.CONFIG_PROPERTIES_FILE_NAME);
    }

    public void selectOutPath(MouseEvent mouseEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser ();
        directoryChooser.setTitle("选择输出目录");//选择对话框的标题
        File selectDirectory = directoryChooser.showDialog(host.getScene().getWindow());
        if(selectDirectory != null){
            exportFilePath.setText(selectDirectory.getPath());
        }
    }
}
