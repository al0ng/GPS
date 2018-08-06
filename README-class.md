### GPS常用类

|GPS常用类|说明|
|------|------|
|MapActivity|用于显示Google Map的Activity类，需要连接底层网络|
|MapView|用于显示地图的View组件，必须和MapActivity配合使用;当MapView获得焦点时，可以控制地图的缩放和移动，MapView只能被MapActivity创建，这是因为mapview需要通过后台线程来链接网络或文件系统，而这些线程要由MapActivity来管理|
|MapController|用于控制地图移动和伸缩，以某个GPS坐标为中心，控制MapView中的View组件，管理Overlay，提供View的基本功能，使用多种地图模式、卫星模式、街景模式来查看GoogleMap|
|Overlay|是一个可显示于地图之上的可绘制的对象;自定义在ＭapView中显示一些自己的东西|
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

#### MapView

|MapView常用方法|说明|
|-------|-------|
|getController()||
|getOverlays()||
|setSatellite(boolean)||
|setTraffic(boolean)||
|setStreetView(boolean)||
|setBuiltInZoomControls(boolean)||

#### LocationManager

|LocationManager常用方法|说明|
|------|------|
|public abstract void onLocationChanged(Location location)|此方法在当位置发生变化后被调用|
|public abstract void onProviderDisabled(String provider)|此方法在provider被用户关闭后被调用|
|public abstract void onProviderEnabled(Location location)|此方法在provider被用户开启后调用|
|public abstract void onStatusChanged(String provider , int status , Bundle extras)|此方法在provider的状态在可用、暂时不可用和无服务三个状态直接切换时被调用|
