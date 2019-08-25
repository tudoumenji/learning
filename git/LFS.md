<https://git-lfs.github.com/>

## Getting Started

1. [Download](https://github.com/git-lfs/git-lfs/releases/download/v2.8.0/git-lfs-windows-v2.8.0.exe) and install the Git command line extension. Once downloaded and installed, set up Git LFS and its respective hooks by running:

   ```
   git lfs install（一般安装git时已安装lfs，此句可不执行）
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