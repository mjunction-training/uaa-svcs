# uaa-svcs
User Authentication and Client Authorization Service

Generate self signed certificate

keytool -genkey -keyalg RSA -dname "cn=MJunction Training, ou=ExpoGrow, o=ExpoGrow.org, l=Kolkata, s=W.B., c=IN" -alias selfsigned -keystore selfsigned.jks -storepass password -validity 360 -keysize 2048  -deststoretype pkcs12

Export public key (password :: 'password')
keytool -export -alias selfsigned -keystore selfsigned.jks -storepass password -rfc -file selfsigned-public.txt


Create below tables and data in the database:

Execute 'maridb-schema.sql'


curl oauth2-read-write-client:Pass%40123@localhost:8769/uaa/oauth/token -d grant_type=password -d username=sysadmin -d password=Admin@123


pm.environment.set("xsrf-token", decodeURIComponent(pm.cookies.get("XSRF-TOKEN")))

?_csrf=5c74d254-4e6f-4cb4-871d-628aa0dae015
