package test02;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private final JTextField lengthField;
    private final JTextArea resultArea;
    private final JButton generateRandomButton;
    private final JButton generateInputButton;
    private final JButton clearButton;
    private final JButton sequentialSearchButton;
    private final JButton selectButton;
    private final JButton bubbleButton;
    private final JButton binarySearchButton;
    private final JButton thirdforwahtButton;
    private int comparisonCount = 0;
    private int[] currentArray = new int[0]; // Store the current array

    public Main() {
        // 设置窗口标题和大小
        setTitle("search search search");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置布局管理器
        setLayout(new BorderLayout());

        // 创建顶部面板用于输入数组长度
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel lengthLabel = new JLabel("输入数组长度 (n): ");
        lengthField = new JTextField(10);
        inputPanel.add(lengthLabel);
        inputPanel.add(lengthField);

        // 创建按钮面板用于放置生成按钮和清除按钮
        JPanel buttonPanel = new JPanel(new FlowLayout());
        generateRandomButton = new JButton("随机生成数组");
        generateInputButton = new JButton("人工输入生成数组");
        clearButton = new JButton("清除结果");
        sequentialSearchButton = new JButton("顺序检索");
        selectButton = new JButton("选择排序");
        bubbleButton = new JButton("冒泡排序");
        binarySearchButton = new JButton("二分检索");
        thirdforwahtButton = new JButton("三分检索");
        buttonPanel.add(generateRandomButton);
        buttonPanel.add(generateInputButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(sequentialSearchButton);
        buttonPanel.add(selectButton);
        buttonPanel.add(bubbleButton);
        buttonPanel.add(binarySearchButton);
        buttonPanel.add(thirdforwahtButton);

        // 创建文本区域用于显示生成的数组
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // 将各个面板添加到窗口中
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // 为随机生成按钮添加动作监听器
        generateRandomButton.addActionListener(e -> {
            try {
                int length = Integer.parseInt(lengthField.getText());
                currentArray = N.generateRandomArray(length);
                displayArray(currentArray);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "请输入有效的整数作为数组长度！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 为人工输入生成按钮添加动作监听器
        generateInputButton.addActionListener(e -> {
            try {
                int length = Integer.parseInt(lengthField.getText());
                currentArray = generateInputArray(length);
                displayArray(currentArray);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "请输入有效的整数作为数组长度！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 为清除按钮添加动作监听器
        clearButton.addActionListener(e -> {
            resultArea.setText("结果已清空，请重新生成或输入数组。\n");
        });

        // 为顺序查找按钮添加动作监听器
        sequentialSearchButton.addActionListener(e -> {
            String targetStr = JOptionPane.showInputDialog("请输入要查找的数字:");
            try {
                int target = Integer.parseInt(targetStr);
                int index = N.sequentialSearch(currentArray, target);
                String result = (index == -1) ? "未找到目标数字" : "目标数字位于索引: " + index;
                resultArea.append(result + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 为选择排序按钮添加动作监听器
        selectButton.addActionListener(e -> {
            currentArray = N.selectionSort(currentArray);
            displayArray(currentArray);
        });

        // 为冒泡排序按钮添加动作监听器
        bubbleButton.addActionListener(e -> {
            currentArray = N.bubbleSort(currentArray);
            displayArray(currentArray);
        });

        // 为二分查找按钮添加动作监听器
        binarySearchButton.addActionListener(e -> {
            String targetStr = JOptionPane.showInputDialog("请输入要查找的数字:");
            try {
                int target = Integer.parseInt(targetStr);
                int index = N.binarySearch(currentArray, target);
                String result = (index == -1) ? "未找到目标数字" : "目标数字位于索引: " + index;
                resultArea.append(result + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 为三分查找按钮添加动作监听器
        thirdforwahtButton.addActionListener(e -> {
            int status = N.checkArrayStatusWithternary(currentArray);
            String statuss = null;
            switch (status) {
                case 1:
                    statuss = "升序";
                    break;
                case 2:
                    statuss = "降序";
                    break;
                case 3:
                    statuss = "先升后降";
                    break;
                case 4:
                    statuss = "先降后升";
                    break;
                default:
                    statuss = "乱序";
                    break;

            }
            JOptionPane.showMessageDialog(this, "当前数组是" + statuss);
            String targetStr = JOptionPane.showInputDialog("请输入要查找的数字:");
            try {
                int target = Integer.parseInt(targetStr);
                int index = N.ternarySearch(currentArray, target);
                String result = (index == -1) ? "未找到目标数字" : "目标数字位于索引: " + index;
                resultArea.append(result + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private int[] generateInputArray(int length) {
        int[] array = new int[length];
        List<Integer> inputList = new ArrayList<>();
        boolean validInput = false;

        while (!validInput) {
            String input = JOptionPane.showInputDialog(this, "请输入" + length + "个整数，用空格隔开：");
            if (input != null) {
                String[] numbers = input.trim().split("\\s+"); // 去掉首尾空格，并用正则分隔

                if (numbers.length == length) {
                    validInput = true;
                    inputList.clear();  // 清空之前的输入数据

                    for (String number : numbers) {
                        try {
                            int num = Integer.parseInt(number.trim());

                            // 检查是否有重复数字
                            if (inputList.contains(num)) {
                                JOptionPane.showMessageDialog(this, "输入的整数有重复，请重新输入！", "错误", JOptionPane.ERROR_MESSAGE);
                                validInput = false;
                                break;
                            } else {
                                inputList.add(num);
                            }

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "输入包含无效字符，请重新输入！", "错误", JOptionPane.ERROR_MESSAGE);
                            validInput = false;
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "输入的整数个数与要求的长度不一致，请重新输入！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "输入不能为空，请重新输入！", "错误", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        // 将List中的元素复制到array中
        for (int i = 0; i < inputList.size(); i++) {
            array[i] = inputList.get(i);
        }

        return array;
    }

    private void displayArray(int[] array) {
        if (array != null) {
            StringBuilder builder = new StringBuilder();
            int checknum = N.checkArrayStatus(array);
            builder.append("当前数组:\n");

            for (int i = 0; i < array.length; i++) {
                builder.append(String.format("%6d", array[i]));
                if ((i + 1) % 10 == 0 || i == array.length - 1) {
                    builder.append("\n");
                }
            }

            builder.append("\n数组检查结果 (1: 升序, 2: 降序, 3: 先升后降, 4: 先降后升): ").append(checknum);
            resultArea.setText(builder.toString());
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}
