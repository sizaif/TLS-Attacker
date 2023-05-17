#!/bin/bash
###
 #                        _oo0oo_
 #                       o8888888o
 #                       88" . "88
 #                       (| -_- |)
 #                       0\  =  /0
 #                     ___/`---'\___
 #                   .' \\|     |// '.
 #                  / \\|||  :  |||// \
 #                 / _||||| -:- |||||- \
 #                |   | \\\  - /// |   |
 #                | \_|  ''\---/''  |_/ |
 #                \  .-\__  '-'  ___/-. /
 #              ___'. .'  /--.--\  `. .'___
 #           ."" '<  `.___\_<|>_/___.' >' "".
 #          | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 #          \  \ `_.   \_ __\ /__ _/   .-` /  /
 #      =====`-.____`.___ \_____/___.-`___.-'=====
 #                        `=---='
 #
 #
 #      ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 #
 #            佛祖保佑     永不宕机     永无BUG
 #
 #        佛曰:
 #                写字楼里写字间，写字间里程序员；
 #                程序人员写程序，又拿程序换酒钱。
 #                酒醒只在网上坐，酒醉还来网下眠；
 #                酒醉酒醒日复日，网上网下年复年。
 #                但愿老死电脑间，不愿鞠躬老板前；
 #                奔驰宝马贵者趣，公交自行程序员。
 #                别人笑我忒疯癫，我笑自己命太贱；
 #                不见满街漂亮妹，哪个归得程序员？
 #
 # @Author: sizaif
 # @Date: 2022-08-03 14:13:45
 # @LastEditTime: 2022-08-03 14:13:47
 # @Description:
 #
###


# 生成DES算法私钥 并生成自签名证书
for len in 512 1024 2048 3072
do
  openssl genpkey -genparam -algorithm DSA -out dsap${len}.pem -pkeyopt dsa_paramgen_bits:${len}
  openssl genpkey -paramfile dsap${len}.pem -out dsa${len}_key.pem
  openssl req -key dsa${len}_key.pem -new -x509 -days 2000 -out dsa${len}_cert.pem -subj "/CN=tls-attacker.com"
done
# 生成RSA算法私钥 并生成自签名证书
for len in 512 1024 2048 4096
do
  openssl genpkey -algorithm RSA -out rsa${len}_key.pem -pkeyopt rsa_keygen_bits:${len}
  openssl req -key rsa${len}_key.pem -new -x509 -days 2000 -out rsa${len}_cert.pem -subj "/CN=tls-attacker.com"
done
# 生成EC椭圆算法的私钥  并生成自签名证书
for named_curve in secp160k1 secp160r1 secp160r2 secp192k1 secp224k1 secp224r1 secp256k1 secp384r1 secp521r1 sect163k1 sect163r1 sect163r2 sect193r1 sect193r2 sect233k1 sect233r1 sect239k1 sect283k1 sect283r1 sect409k1 sect409r1 sect571k1 sect571r1
do
  openssl ecparam -name ${named_curve} -genkey -out ec_${named_curve}_key.pem
  openssl req -key ec_${named_curve}_key.pem -new -x509 -days 2000 -out ec_${named_curve}_cert.pem -subj "/CN=tls-attacker.com"
done

#
openssl req -x509 -new -nodes -extensions v3_ca -key rsa2048_key.pem -days 2000 -out rsa_ca.pem -sha256 -subj "/CN=TLS-Attacker CA"
openssl req -x509 -new -nodes -extensions v3_ca -key dsa1024_key.pem -days 2000 -out dsa_ca.pem -sha256 -subj "/CN=TLS-Attacker CA"

# 生成 dh参数 dhparam.pem
openssl dhparam -out dhparam.pem 1024
# 根据dh参数 生成 dh私钥
openssl genpkey -paramfile dhparam.pem -out dhkey.pem
# pkey命令用于处理公钥或私钥，
# 根据dh私钥生成dh公钥
openssl pkey -in dhkey.pem -pubout -out dhpubkey.pem
# 使用 rsa2048_key.pem 生成自签名证书 rsa.csr
openssl req -new -key rsa2048_key.pem -out rsa.csr -subj "/CN=tls-attacker.com"
openssl x509 -req -in rsa.csr -CAkey rsa2048_key.pem -CA rsa_ca.pem -force_pubkey dhpubkey.pem -outrsa_dhcert.pem -CAcreateserial
openssl req -new -key dsa1024_key.pem -out dsa.csr -subj "/CN=tls-attacker.com"
openssl x509 -req -in dsa.csr -CAkey dsa1024_key.pem -CA dsa_ca.pem -force_pubkey dhpubkey.pem -out
dsa_dhcert.pem -CAcreateserial

for named_curve in secp160k1 secp160r1 secp160r2 secp192k1 secp224k1 secp224r1 secp256k1 secp384r1 secp521r1 sect163k1 sect163r1 sect163r2 sect193r1 sect193r2 sect233k1 sect233r1 sect239k1 sect283k1 sect283r1 sect409k1 sect409r1 sect571k1 sect571r1
do
  openssl ecparam -out ec_param_${named_curve}.pem -name ${named_curve}
	openssl genpkey -paramfile ec_param_${named_curve}.pem -out ec_rsa_private_key_${named_curve}.pem
  openssl pkey -in ec_rsa_private_key_${named_curve}.pem -pubout -out ec_rsa_public_key_${named_curve}.pem
	openssl x509 -req -in rsa.csr -CAkey rsa2048_key.pem -CA rsa_ca.pem -force_pubkey ec_rsa_public_key_${named_curve}.pem -out ec_rsa_cert_${named_curve}.pem -CAcreateserial
done
