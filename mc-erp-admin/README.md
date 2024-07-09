
- 应用概述： admin 模块可能是项目的管理界面，提供对系统的监控和管理功能。
- 主要功能： 包含管理界面的业务逻辑、前端代码、可能的后台服务等。
- 角色定位： 为管理员提供系统监控、用户管理等功能。


### jwt 公钥、私钥生成

#### 生成使用指定密码加密的 2048 位 RSA 私钥
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048 -pass pass:mypassword

> openssl genpkey 用于生成私钥。常用参数如下：
> - algorithm <algorithm>：指定生成密钥的算法。例如，RSA, DSA, DH, EC 等。对于 RSA 密钥，使用 RSA。
> - out <filename>：指定输出文件的名称。
> - pkeyopt <option>：指定生成密钥的选项。对于 RSA 密钥，可以使用 rsa_keygen_bits 来指定密钥的位长度。
> - pass <arg>：指定密码参数，用于加密私钥。可以是 pass:password 或 file:filename。


#### 使用指定密码解密私钥并提取公钥
openssl rsa -in private_key.pem -pubout -out public_key.pem -passin pass:mypassword

> openssl rsa 命令用于处理 RSA 密钥。例如，可以从私钥中提取公钥，或者将密钥转换为不同的格式。
> - in <filename>：指定输入文件的名称（通常是私钥文件）。
> - pubout：从私钥中提取公钥。
> - out <filename>：指定输出文件的名称。
> - passin <arg>：指定用于解密私钥的密码参数。