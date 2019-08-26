<https://git-lfs.github.com/>

## Getting Started

1. [Download](https://github.com/git-lfs/git-lfs/releases/download/v2.8.0/git-lfs-windows-v2.8.0.exe) and install the Git command line extension. Once downloaded and installed, set up Git LFS and its respective hooks by running:

   ```
   git lfs install（初始化，一般安装git时已安装lfs，是否可以不执行待验证）
   ```

   You'll need to run this in your repository directory, once per repository.

2. Select the file types you'd like Git LFS to manage (or directly edit your .gitattributes). You can configure additional file extensions at anytime.

   ```
   git lfs track "*.psd"
   ```

   Make sure .gitattributes is tracked

   ```
   git add .gitattributes
   ```

3. There is no step three. Just commit and push to GitHub as you normally would.

   ```
   git add file.psd
   git commit -m "Add design file"
   git push origin master
   ```





注意：

1、github规定上传的单个文件不能超过100m（我测试了超过50m就警告，但是不报错，超过100m才报错）。

2、目前 Git LFS的总存储量为1G左右，超过需要付费(具体的去自己的github查看自己的账单：账户->Settings -> Billing)，所以要省着点用（上传的时候用git push，但下载的时候最好直接下载整个zip，跳过lfs流量，当然付费用户可以直接无视）

3、超出1G配额后会提示"batch response: This repository is over its data quota. Purchase more data packs to restore access", 意思就是"仓库超出限额，需要配置更多的额度"，掏钱吧！
