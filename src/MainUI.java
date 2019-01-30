import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

class MainUI {
    private JPanel ui = new JPanel();
    private JTextField inURL;
    private JTextField outURL;
    private JButton inButton;
    private JButton outButton;
    private JButton processButton;
    private JButton settingsButton;
    private JTextArea log = new JTextArea();
    private JFileChooser fileChooser = new JFileChooser();
    private FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "逗号分隔符文件(*.csv)", "csv");
    private SettingsUI settingsUI;

    MainUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*} catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }*/
        JFrame frame = new JFrame("学生列表CSV生成器");
        frame.setContentPane(ui);
        ui.setLayout(null);
        addComponents();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        fileChooser.setFileFilter(filter);
        addListeners();
        settingsUI = new SettingsUI(frame);
    }

    private void addComponents() {
        JLabel in = new JLabel("读取文件:");
        in.setSize(80, 30);
        in.setLocation(0, 0);
        ui.add(in);
        JLabel out = new JLabel("生成文件:");
        out.setSize(80, 30);
        out.setLocation(0, in.getHeight());
        ui.add(out);
        inURL = new JTextField();
        inURL.setSize(300, in.getHeight());
        inURL.setLocation(in.getWidth(), 0);
        ui.add(inURL);
        outURL = new JTextField();
        outURL.setSize(300, out.getHeight());
        outURL.setLocation(out.getWidth(), in.getHeight());
        ui.add(outURL);
        inButton = new JButton("选择");
        inButton.setSize(70, in.getHeight());
        inButton.setLocation(inURL.getX() + inURL.getWidth(), 0);
        ui.add(inButton);
        outButton = new JButton("选择");
        outButton.setSize(70, out.getHeight());
        outButton.setLocation(outURL.getX() + outURL.getWidth(), in.getHeight());
        ui.add(outButton);
        processButton = new JButton("处理");
        processButton.setSize(in.getWidth() + inURL.getWidth() + inButton.getWidth() - 70, in.getHeight());
        processButton.setLocation(0, out.getY() + out.getHeight());
        ui.add(processButton);
        settingsButton = new JButton("设置");
        settingsButton.setSize(70, processButton.getHeight());
        settingsButton.setLocation(processButton.getWidth(), processButton.getY());
        ui.add(settingsButton);
        log.setSize(in.getWidth() + inURL.getWidth() + inButton.getWidth(), 100);
        log.setLocation(0, processButton.getY() + processButton.getHeight());
        log.setEditable(false);
        ui.add(log);
        ui.setPreferredSize(new Dimension(in.getWidth() + inURL.getWidth() + inButton.getWidth(),
                in.getHeight() + out.getHeight() + processButton.getHeight() + log.getHeight()));
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        if (path.substring(path.length() - 1).equals("/"))
            path = path.substring(0, path.length() - 1);
        fileChooser.setCurrentDirectory(new File(path));
    }

    private void addListeners() {
        inButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!((JButton)e.getSource()).isEnabled()) return;
                fileChooser.setDialogTitle("请选择待处理的文件...");
                if (fileChooser.showOpenDialog(ui) == JFileChooser.APPROVE_OPTION) {
                    inURL.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        outButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!((JButton)e.getSource()).isEnabled()) return;
                fileChooser.setDialogTitle("请选择处理后文件的保存位置...");
                if (fileChooser.showSaveDialog(ui) == JFileChooser.APPROVE_OPTION) {
                    outURL.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
                if (outURL.getText().length() >= 4 &&
                        !outURL.getText().substring(outURL.getText().length() - 4).equals(".csv"))
                    outURL.setText(outURL.getText() + ".csv");
            }
        });
        processButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!((JButton)e.getSource()).isEnabled()) return;
                if (!checkIO()) return;
                disableButtons();
                startProcess();
            }
        });
        settingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!((JButton)e.getSource()).isEnabled()) return;
                settingsUI.show();
            }
        });
    }

    private boolean checkIO() {
        if (!FileRead.checkExist(inURL.getText()) && !FileRead.checkIsFile(inURL.getText())) {
            JOptionPane.showMessageDialog(ui, "输入文件不存在或无法读取.", "错误",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (FileRead.checkExist(outURL.getText())){
            if (FileRead.checkIsFile(outURL.getText())) {
                return JOptionPane.showConfirmDialog(ui, "输出文件已存在,确认追加?", "提示",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            } else {
                JOptionPane.showMessageDialog(ui, "输出文件无法写入.", "错误",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private void disableButtons() {
        inButton.setEnabled(false);
        outButton.setEnabled(false);
        processButton.setEnabled(false);
        settingsButton.setEnabled(false);
    }

    private void enableButtons() {
        inButton.setEnabled(true);
        outButton.setEnabled(true);
        processButton.setEnabled(true);
        settingsButton.setEnabled(true);
    }

    private void startProcess() {
        String message = "未知错误.";
        String title = "错误.";
        int icon = JOptionPane.ERROR_MESSAGE;
        switch (CoreProcess.start(inURL.getText(), outURL.getText())) {
            case 0:
                message = "成功导出处理数据.";
                title = "成功.";
                icon = JOptionPane.INFORMATION_MESSAGE;
                break;
            case -1:
                message = "打开文件时发生错误.";
                break;
            case -2:
                message = "读取文件时发生错误.";
                break;
            case -3:
                message = "写入文件时发生错误.";
                break;
            case -4:
                message = "部分学号未找到对应姓名.";
        }
        JOptionPane.showMessageDialog(ui, message, title, icon);
        enableButtons();
        log.setText(CoreProcess.output.toString());
    }
}
