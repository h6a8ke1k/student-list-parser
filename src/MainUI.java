import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainUI {
    private JPanel ui = new JPanel();
    private JTextField inURL;
    private JTextField outURL;
    private JButton inButton;
    private JButton outButton;
    private JButton processButton;
    private JFileChooser fileChooser = new JFileChooser();
    private FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "逗号分隔符文件(*.csv)", "csv");

    public MainUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
        } catch (InstantiationException e) {
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            //e.printStackTrace();
        }
        JFrame frame = new JFrame("学生列表CSV生成器");
        frame.setContentPane(ui);
        ui.setLayout(null);
        addComponents();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        fileChooser.setFileFilter(filter);
        addListeners();
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
        inURL.setSize(100, in.getHeight());
        inURL.setLocation(in.getWidth(), 0);
        ui.add(inURL);
        outURL = new JTextField();
        outURL.setSize(100, out.getHeight());
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
        processButton.setSize(in.getWidth() + inURL.getWidth() + inButton.getWidth(), in.getHeight());
        processButton.setLocation(0, out.getY() + out.getHeight());
        ui.add(processButton);
        ui.setPreferredSize(new Dimension(in.getWidth() + inURL.getWidth() + inButton.getWidth(),
                in.getHeight() + out.getHeight() + processButton.getHeight()));
    }

    private void addListeners() {
        inButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fileChooser.setDialogTitle("请选择待处理的文件...");
                if (fileChooser.showOpenDialog(ui) == JFileChooser.APPROVE_OPTION) {
                    inURL.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        outButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fileChooser.setDialogTitle("请选择处理后文件的保存位置...");
                if (fileChooser.showSaveDialog(ui) == JFileChooser.APPROVE_OPTION) {
                    outURL.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        processButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }
}
