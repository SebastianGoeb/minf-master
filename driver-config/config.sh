#!/bin/bash

# get sudo rights
if [[ $EUID != 0 ]]; then
    sudo ${BASH_SOURCE[0]}
    exit
fi

declare -A name_to_ip

name_to_ip=(
    ["nsl200"]="10.5.1.2" ["nsl201"]="10.5.1.3"
    ["nsl202"]="10.5.1.4" ["nsl203"]="10.5.1.5"
    ["nsl204"]="10.5.1.6" ["nsl205"]="10.5.1.7"
    ["nsl206"]="10.5.1.8" ["nsl207"]="10.5.1.9"
    ["nsl208"]="10.5.1.10" ["nsl209"]="10.5.1.11"
)

setup()
{
    ip=${name_to_ip[$HOSTNAME]}
    echo $ip

    ip link set dev eth1 up
    ip addr add $ip/24 dev eth1
}

reset()
{
    ip addr flush dev eth1
}

reset
setup
