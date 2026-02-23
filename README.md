![9d985140cb8ffbee608bae3fd6b385cd](https://github.com/user-attachments/assets/b295b0c3-ff46-431f-8540-e3ad5bdb883e)
![1d3155b9d3e194d59073791cf54c2866](https://github.com/user-attachments/assets/c9ac4935-cb71-40bc-a6e9-f87b69ee8ef2)
![b689e756446144ab700cb0a4666ebf89](https://github.com/user-attachments/assets/093f5a90-c989-4ab6-92d2-b77442faa434)
![7fa0517c3df9bee825c50f51e0229151](https://github.com/user-attachments/assets/a230b591-4422-41d4-9abc-bbd5397aec18)
![c2ffe12c6ca094a1d146523cf55f559d](https://github.com/user-attachments/assets/b67f793b-b847-4731-8804-42d6ab32b08e)
![cb0323fe9949c734f701c5431e651735](https://github.com/user-attachments/assets/960d4f87-6578-47df-be25-64934e31ff3d)

*APP简介
小甜饼，登录以后，暂时只有在本地上传商品(点击主页中间的加号)，查看商品详情(点击商品文字)的主要功能。当时是想模仿咸鱼然后又想模仿拼多多但是没有服务器技术有限只实现了这两个主要功能。
图一 登录功能；图二，查看商品详情与购买成功；图三，心理热线，，；图四，本地上传图片，发布商品；图五，没有网络时只展现本地商品；图六，退出登录。

*心得体会
1.第三方库很便利但是在下还不太会使用，，
2.在本地上传图片那个部分 本来是按着第一行代码上面写的，已经舍弃了适配低版本的写法，结果我在试运行的时候还是不行。然后询问ai又换了种更简单的新写法。那个路径不用分那么多只需要一个path！
还有startAcitivtyForResult这个方法在打出来的时候会被画上线好像就是过时了？嗯其实说这么多我就是有点疑惑该怎么去获取跟得上技术刷新的学习捏

*待提升优化的地方
1.写的时候发现好多本来想着挺简单的功能都没有能力实现。比如那个没开发的搜索功能…
2.目前跳转页面就会intent这一种，但是它跳转时传入的数据似乎不能太大，当时直接传入Uri后在运行时就直接闪退了。
