查看文件个数

ls -l | grep "^-" |wc -l

查看文件夹大小

du - -max -depth 1 -lh 该文件夹的完整路径