##Install
sudo apt-get update
sudo apt-get upgrade
sudo apt-get install bluetooth bluez-utils blueman

##Command Line Access(CLA)
###status:
/etc/init.d/bluetooth status
###Scan
hcitool scan
###ping
sudo l2ping -c 1 [68:69:7C:31:9A:75]
