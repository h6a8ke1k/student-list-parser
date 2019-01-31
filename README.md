# Student List Parser

***Notice*: The project is designed for Windows only! If you use it on other platforms, please remember to change the code where the program determines input files' encoding.**

## Features
1. Parse student information from disordered CSV file (converted from Excel) to a CSV file with a standard format.
2. Smartly ignore irrelevant information and warn the user when something suspicious (maybe error) is detected.

## Usage
1. Convert the Excel file to CSV file(s). If you have multiple tabs, you can only convert one tab a time if you use Microsoft Office Excel.
2. Select a CSV file as input and input a path for the output file. If the output file already exists, the program will **append** the file instead of *overriding* it.

## Settings
1. Ignore strings: if the content in a cell **equals** one of the ignore strings, then the content in the cell will be ignored.
2. Warning strings: if the content in a cell **contains** one of the warning strings, then the content in the cell will trigger a warning message in the log.


# 学生列表CSV生成器

***注意*: 此项目仅为Windows系统设计！如果你想要在其他平台上使用，请记得改变程序判断输入文件编码的代码。**

## 特性
1. 分析包含学生信息的混乱CSV文件(从Excel转换而来)，输出标准格式的学生列表CSV。
2. 智能忽略无关信息，并在发现可疑信息时(可能是错误)警告用户。

## 使用方法
1. 转换包含学生信息的Excel文件，输出为CSV格式。如果Excel文件有多个标签，用Microsoft Office一次只能保存一个标签。
2. 选择一个CSV文件作为输入，指定一个CSV为输出。如果输出文件已经存在，程序会把结果**附加**到文件末尾，而非*覆盖*它。

## 设置
1. 忽略字符串：如果一个单元格的内容与忽略字符串之一**相同**，那么单元格的内容将被忽略。
2. 警告字符串：如果一个单元格的内容**包含**警告字符串之一，那么单元格的内容将触发一条日志中的警告。
