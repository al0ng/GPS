### GPS常用类

|GPS常用类|说明|
|------|------|
|MapActivity|用于显示Google Map的Activity类，需要连接底层网络|
|MapView|用于显示地图的View组件，必须和MapActivity配合使用|
|MapController|用于控制地图移动和伸缩，以某个GPS坐标为中心，控制MapView中的View组件，管理Overlay，提供View的基本功能，使用多种地图模式、卫星模式、街景模式来查看GoogleMap|
|Overlay|是一个可显示于地图之上的可绘制的对象|
|GeoPoint|是一个包含经纬度位置的对象|
|LocationManager|本类提供访问定位服务的功能，也提供了获取最佳定位提供者的功能，另外临近警报功能|
|LocationProvider|该类是定位提供者的抽象类，定位提供者具备周期性报告设备地理位置的功能|
|LocationListener|提供定位信息发生变化时的回调功能，必须事先在定位管理器中注册监听器对象|
|Criteria|该类使得应用能够通过在LocationProvider中设置的属性来选择合适的定位提供者|

#### MapController

|MapController常用的方法|说明|
|------|------|
|animateTo(GeoPoint point)||
|setCenter(GeoPoint point)||
|setZoom(int zoomLevel)||
