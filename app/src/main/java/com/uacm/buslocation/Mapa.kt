package com.uacm.buslocation

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.Location
import android.os.*
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Mapa : AppCompatActivity(), OnMapReadyCallback {

    lateinit var myHandler : Handler
    lateinit var myHandlerRegreso: Handler

    var arrayLat = arrayOf(19.345335442352848,19.34508642007505,19.344938851138693,19.344800505139688,
        19.344246920599417,19.34409368744477,19.343796234435814,19.343271746057468,
        19.342698053599705,19.342130462269267,19.34195347102073,19.34174596379539,
        19.341734333259723,19.341262090106383,19.34052673733295,19.339852098847274,
        19.339703386106578,19.339521607384242,19.339014316856236,19.338942450571224,
        19.338950905429755,19.338697129965013,19.338099031466072,19.33775077057058,
        19.337394938018917,19.337099672122065,19.33715266860395,19.337871904879194,
        19.33831858686581,19.338038464407234,19.33777348326049,19.337516072591555,
        19.33703153376166,19.336491920468333,19.33596195272547,19.335381433222977,
        19.334841837013826,19.33430223902161,19.33366323909287,19.332967436327593,
        19.332498832794798,19.331746224306087,19.330780608336042,19.330289902051973,
        19.329682175729218,19.32912807034704,19.32850246523617,19.328019853942962,
        19.32751042367424,19.326920561650184,19.32638432159871,19.32598214040523,
        19.32514157916303,19.324560646035707,19.32364902373361,19.323065197037025,
        19.322368068176644,19.321608373084075,19.32096486388967,19.32061629535201,
        19.320285601435373,19.32024091301726,19.320669926567916,19.32018729213857,
        19.31932927185518,19.31861902796902,19.317877191078452,19.317206853642062,
        19.31648288612348,19.31548183933394,19.315714226651608,19.316000240238964,
        19.31619687429044,19.316187936383514,19.31592873688902,19.315767854473535,
        19.315687413088554,19.31567847515369,19.315755542815737,19.315727903643847,
        19.315492970493864,19.315009283534778,19.314304479975036,19.313115980815212,
        19.312480268461144,19.311664894591587,19.310504016303497,19.309743912959846,
        19.309080547154196,19.308389538333913,19.30756032378875,19.306938410121077,
        19.30645270664884,19.305886070454406,19.30539713389001,19.30493055341573,
        19.30456765657061,19.305486656519633,19.30661261213986,19.307640247682357,
        19.308895606461334,19.31020272237153,19.31138041191676,19.31211807211065,
        19.312920445520447,19.31365810792255,19.314848713901583,19.314796948604524,
        19.314279294732586,19.313593400827116
    )

    var arrayLong = arrayOf(-99.06545329319775,-99.06489612150645,-99.06458006505046,-99.06434546644364,
        -99.06433742935478,-99.06435335105984,-99.06442340655872,-99.06442599484838,
        -99.06444539952332,-99.06443246307315,-99.06367568075747,-99.06285421619293,
        -99.06274283856513,-99.06311463125398,-99.06367947014651,-99.0642371591798,
        -99.06432314252032,-99.06439034583663,-99.06445754915248,-99.06463675799566,
        -99.06499069546102,-99.06516890685718,-99.06529728385877,-99.06531333098397,
        -99.06487203504079,-99.06447888047362,-99.06426224428364,-99.06424619715843,
        -99.06422212647043,-99.06358826502527,-99.0631309219569,-99.06276986164012,
        -99.06232054213456,-99.06181120869118,-99.06135386562322,-99.06075254245539,
        -99.06030108229642,-99.05975933010565,-99.05917243189899,-99.05848019298855,
        -99.05795348947012,-99.05723115321577,-99.05626803820994,-99.05579167691211,
        -99.05514764513053,-99.05460779496092,-99.0539542921235,-99.05347126828757,
        -99.05295036770487,-99.05239157542402,-99.05188013842101,-99.05146341197414,
        -99.05116115287963,-99.05094331860083,-99.0517104741051,-99.05215975700389,
        -99.05269013611812,-99.05329628367724,-99.05386454701393,-99.0545275209067,
        -99.05519049479952,-99.05572087391326,-99.05647853524592,-99.05694261697063,
        -99.05763400403049,-99.05821951565109,-99.05882566320973,-99.0593749844352,
        -99.05993377671649,-99.06080511383225,-99.06214053378729,-99.06343806840579,
        -99.0646408924682,-99.06506708997094,-99.06536069269465,-99.0655122270391,
        -99.06625096937677,-99.06716019071546,-99.06801057765038,-99.0689917182032,
        -99.06956282986788,-99.06984106375599,-99.06979713208943,-99.06979713208943,
        -99.06972391244041,-99.06970926855156,-99.06966533688502,-99.0695628296631,
        -99.06918208855305,-99.06843524907599,-99.06760054741169,-99.06688299685814,
        -99.06545832395165,-99.06381820840107,-99.06226777679089,-99.06083957130288,
        -99.0598233481672,-99.05905255214915,-99.0581337701594,-99.05725169990234,
        -99.05623692576437,-99.05518101213434,-99.05427594330895,-99.05518099456518,
        -99.05622319503084,-99.0572379691688,-99.05841730127507,-99.05874641721171,
        -99.05910295947605,-99.05973376502126)


    //Linea para mover marker
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var mDatabase: DatabaseReference

    private lateinit var map:GoogleMap
    private lateinit var boton : Button
    private lateinit var boton2: Button

    companion object{
        const val REQUEST_CODE_LOCATION=0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mDatabase = FirebaseDatabase.getInstance().reference

        //evitamos rotación en pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        boton = findViewById(R.id.btnregresar)
        boton2= findViewById(R.id.abordar)

        boton.setOnClickListener {
            val intent = Intent(this,BuscarRuta::class.java)
            startActivity(intent)
        }

        boton2.setOnClickListener{
            val intent = Intent(this,BuscarRuta::class.java)
            startActivity(intent)
        }

        createFragment()
    }

    //metodo para mover el marcador, si no funciona borrar
    private fun hacerZoom() {
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                Log.e("Latitud: "+location!!.latitude," Longitud: "+ location.longitude)
                var pos = LatLng(location.latitude,location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,13.2f),3000,null)
            }
        return
    }

    //contenedor de nuestro mapa
    private fun createFragment(){
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    //Se llama cuando el mapa esta creado
    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        moverMarker()
        //markerRegreso()
        createPolyline()

        //meter un if con una bandera para que cuando presiones el boton de abordar
        //desaparezcala ubicación en tiempo Real y así simular que el usuario ya abordo
        aceptaUbiciacion()
    }

    private fun createPolyline(){
        val polylineOption = PolylineOptions()
            .add(LatLng(19.345335442352848,-99.06545329319775))
            .add(LatLng(19.34508642007505,-99.06489612150645))
            .add(LatLng(19.344938851138693,-99.06458006505046))
            .add(LatLng(19.344800505139688,-99.06434546644364))
            .add(LatLng(19.344246920599417,-99.06433742935478))
            .add(LatLng(19.34409368744477,-99.06435335105984))
            .add(LatLng(19.343796234435814,-99.06442340655872))
            .add(LatLng(19.343271746057468,-99.06442599484838))
            .add(LatLng(19.342698053599705,-99.06444539952332))
            .add(LatLng(19.342130462269267,-99.06443246307315))
            .add(LatLng(19.34195347102073,-99.06367568075747))
            .add(LatLng(19.34174596379539,-99.06285421619293))
            .add(LatLng(19.341734333259723,-99.06274283856513))
            .add(LatLng(19.341262090106383,-99.06311463125398))
            .add(LatLng(19.34052673733295,-99.06367947014651))
            .add(LatLng(19.339852098847274,-99.0642371591798))
            .add(LatLng(19.339703386106578,-99.06432314252032))
            .add(LatLng(19.339521607384242,-99.06439034583663))
            .add(LatLng(19.339014316856236,-99.06445754915248))
            .add(LatLng(19.338942450571224,-99.06463675799566))
            .add(LatLng(19.338950905429755,-99.06499069546102))
            .add(LatLng(19.338697129965013,-99.06516890685718))
            .add(LatLng(19.338099031466072,-99.06529728385877))
            .add(LatLng(19.33775077057058,-99.06531333098397))
            .add(LatLng(19.337394938018917,-99.06487203504079))
            .add(LatLng(19.337099672122065,-99.06447888047362))
            .add(LatLng(19.33715266860395,-99.06426224428364))
            .add(LatLng(19.337871904879194,-99.06424619715843))
            .add(LatLng(19.33831858686581,-99.06422212647043))
            .add(LatLng(19.338038464407234,-99.06358826502527))
            .add(LatLng(19.33777348326049,-99.0631309219569))
            .add(LatLng(19.337516072591555,-99.06276986164012))
            .add(LatLng(19.33703153376166,-99.06232054213456))
            .add(LatLng(19.336491920468333,-99.06181120869118))
            .add(LatLng(19.33596195272547,-99.06135386562322))
            .add(LatLng(19.335381433222977,-99.06075254245539))
            .add(LatLng(19.334841837013826,-99.06030108229642))
            .add(LatLng(19.33430223902161,-99.05975933010565))
            .add(LatLng(19.33366323909287,-99.05917243189899))
            .add(LatLng(19.332967436327593,-99.05848019298855))
            .add(LatLng(19.332498832794798,-99.05795348947012))
            .add(LatLng(19.331746224306087,-99.05723115321577))
            .add(LatLng(19.330780608336042,-99.05626803820994))
            .add(LatLng(19.330289902051973,-99.05579167691211))
            .add(LatLng(19.329682175729218,-99.05514764513053))
            .add(LatLng(19.32912807034704,-99.05460779496092))
            .add(LatLng(19.32850246523617,-99.0539542921235))
            .add(LatLng(19.328019853942962,-99.05347126828757))
            .add(LatLng(19.32751042367424,-99.05295036770487))
            .add(LatLng(19.326920561650184,-99.05239157542402))
            .add(LatLng(19.32638432159871,-99.05188013842101))
            .add(LatLng(19.32598214040523,-99.05146341197414))
            .add(LatLng(19.32514157916303,-99.05116115287963))
            .add(LatLng(19.324560646035707,-99.05094331860083))
            .add(LatLng(19.32364902373361,-99.0517104741051))
            .add(LatLng(19.323065197037025,-99.05215975700389))
            .add(LatLng(19.322368068176644,-99.05269013611812))
            .add(LatLng(19.321608373084075,-99.05329628367724))
            .add(LatLng(19.32096486388967,-99.05386454701393))
            .add(LatLng(19.32061629535201,-99.0545275209067))
            .add(LatLng(19.320285601435373,-99.05519049479952))
            .add(LatLng(19.32024091301726,-99.05572087391326))
            .add(LatLng(19.320669926567916,-99.05647853524592))
            .add(LatLng(19.32018729213857,-99.05694261697063))
            .add(LatLng(19.31932927185518,-99.05763400403049))
            .add(LatLng(19.31861902796902,-99.05821951565109))
            .add(LatLng(19.317877191078452,-99.05882566320973))
            .add(LatLng(19.317206853642062,-99.0593749844352))
            .add(LatLng(19.31648288612348,-99.05993377671649))
            .add(LatLng(19.31548183933394,-99.06080511383225))
            .add(LatLng(19.315714226651608,-99.06214053378729))
            .add(LatLng(19.316000240238964,-99.06343806840579))
            .add(LatLng(19.31619687429044,-99.0646408924682))
            .add(LatLng(19.316187936383514,-99.06506708997094))
            .add(LatLng(19.31592873688902,-99.06536069269465))
            .add(LatLng(19.315767854473535,-99.0655122270391))
            .add(LatLng(19.315687413088554,-99.06625096937677))
            .add(LatLng(19.31567847515369,-99.06716019071546))
            .add(LatLng(19.315755542815737,-99.06801057765038))
            .add(LatLng(19.315727903643847,-99.0689917182032))
            .add(LatLng(19.315492970493864,-99.06956282986788))
            .add(LatLng(19.315009283534778,-99.06984106375599))
            .add(LatLng(19.314304479975036,-99.06979713208943))
            .add(LatLng(19.313115980815212,-99.06979713208943))
            .add(LatLng(19.312480268461144,-99.06972391244041))
            .add(LatLng(19.311664894591587,-99.06970926855156))
            .add(LatLng(19.310504016303497,-99.06966533688502))
            .add(LatLng(19.309743912959846,-99.0695628296631))
            .add(LatLng(19.309080547154196,-99.06918208855305))
            .add(LatLng(19.308389538333913,-99.06843524907599))
            .add(LatLng(19.30756032378875,-99.06760054741169))
            .add(LatLng(19.306938410121077,-99.06688299685814))
            .add(LatLng(19.30645270664884,-99.06545832395165))
            .add(LatLng(19.305886070454406,-99.06381820840107))
            .add(LatLng(19.30539713389001,-99.06226777679089))
            .add(LatLng(19.30493055341573,-99.06083957130288))
            .add(LatLng(19.30456765657061,-99.0598233481672))
            .add(LatLng(19.305486656519633,-99.05905255214915))
            .add(LatLng(19.30661261213986,-99.0581337701594))
            .add(LatLng(19.307640247682357,-99.05725169990234))
            .add(LatLng(19.308895606461334,-99.05623692576437))
            .add(LatLng(19.31020272237153,-99.05518101213434))
            .add(LatLng(19.31138041191676,-99.05427594330895))
            .add(LatLng(19.31211807211065,-99.05518099456518))
            .add(LatLng(19.312920445520447,-99.05622319503084))
            .add(LatLng(19.31365810792255,-99.0572379691688))
            .add(LatLng(19.314848713901583,-99.05841730127507))
            .add(LatLng(19.314796948604524,-99.05874641721171))
            .add(LatLng(19.314279294732586,-99.05910295947605))
            .add(LatLng(19.313593400827116,-99.05973376502126))
            .color(ContextCompat.getColor(this,R.color.purple_bus))

        val polyline : Polyline = map.addPolyline(polylineOption)
        //inicio de la linea redondeado
        polyline.startCap = RoundCap()
        //Final de la linea redondeado
        polyline.endCap = RoundCap()
    }

    private fun permisoUbicacion() =
        ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun aceptaUbiciacion(){
        if(!::map.isInitialized) return
        if(permisoUbicacion()){
            map.isMyLocationEnabled = true
        }else{
            requiereUbicacion()
        }
    }

    private fun requiereUbicacion(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled=true
            }else{
                Toast.makeText(this,"Para activar la localización ve a ajustes y acepta los permisos",Toast.LENGTH_SHORT).show()
            }else ->{}
        }
    }

    //Crear punteros o marcas
    private fun createMarker(){
        val coordenadasUser = LatLng(19.3301238,-99.0558079)
        val marcaUser = map.addMarker(
            MarkerOptions()

                .position(coordenadasUser)
                .title("UACM")
        )
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordenadasUser,13.2f),1,null)
    }

    public fun contador(){
        var i=0
        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.e("seconds remaining: ","hola: "+i)
                i++
            }
            override fun onFinish() {
                Toast.makeText(this@Mapa,"Actualizado",Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    public fun moverMarker(){
        hacerZoom()
        var k = 0
        myHandler = Handler(Looper.getMainLooper())
        myHandler.post(object : Runnable{
            override fun run() {

                lanzaNotificacionRetardo()
                lanzaNotificacionLleno()
                lanzaNotificacionTrafico()

                var coordenadas = LatLng(arrayLat[k],arrayLong[k])
                var userMarca = map
                userMarca.clear()
                createPolyline()
                userMarca.addMarker(
                    MarkerOptions()
                        .position(coordenadas)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.autobus1)).anchor(0.0f,1.0f)
                )
                k++
                val postDelayed: Any = myHandler.postDelayed(this,2000)
            }
        })
    }

    public fun markerRegreso(){
        hacerZoom()
        var i =109

        myHandlerRegreso = Handler(Looper.getMainLooper())
        myHandlerRegreso.post(object : Runnable{
            override fun run() {
                lanzaNotificacionTrafico()
                lanzaNotificacionLleno()
                lanzaNotificacionRetardo()

                var coordenadasRegreso = LatLng(arrayLat[i],arrayLong[i])
                var userMarker = map
                userMarker.clear()
                createPolyline()
                userMarker.addMarker(
                    MarkerOptions()
                        .position(coordenadasRegreso)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.autobus1)).anchor(0.0f,1.0f)
                )
                i--
                val postDelayed: Any = myHandlerRegreso.postDelayed(this,2000)
            }
        })
    }

    fun createSimpleNotificationRetardo() {

        val intent = Intent(this, Mapa::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, flag)

        var builder = NotificationCompat.Builder(this, BuscarRuta.MY_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_map)
            .setContentTitle("Unidad con retardo")
            .setContentText("Parece que la unidad va lenta, considera tomar otra ruta")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Parece que la unidad va lenta, considera tomar otra ruta")
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }

    fun createSimpleNotificationTrafico() {

        val intent = Intent(this, BuscarRuta::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent:PendingIntent = PendingIntent.getActivity(this, 0, intent, flag)

        var builder = NotificationCompat.Builder(this, BuscarRuta.MY_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_map)
            .setContentTitle("Trafico en la ruta")
            .setContentText("Trafico pesado, considera tomar otra ruta")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Parece que la unidad va lenta, considera tomar otra ruta")
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }

    fun createSimpleNotificationLleno() {

        val intent = Intent(this, BuscarRuta::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent:PendingIntent = PendingIntent.getActivity(this, 0, intent, flag)

        var builder = NotificationCompat.Builder(this, BuscarRuta.MY_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_map)
            .setContentTitle("Unidad Llena")
            .setContentText("Posiblemente no puedas abordar la próxima unidad")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Posiblemente no puedas abordar la próxima unidad")
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }

    public fun lanzaNotificacionRetardo(){

        //obtengo el valor de la bandera
        mDatabase.child("bandera").child("valor").get().addOnSuccessListener {
            val res = it.value
            Log.i("El dato ", "es: "+res)
            if(res == "1"){
                createSimpleNotificationRetardo()
                var mapAct = mapOf(
                    "valor" to "0"
                )
                //Actualizo el valor a 0
                mDatabase.child("bandera").updateChildren(mapAct)
            }else{
                Log.e("Error","No se lanzo notificación")
            }


        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)

        }
    }

    public fun lanzaNotificacionLleno(){

        //obtengo el valor de la bandera
        mDatabase.child("bandera").child("valor").get().addOnSuccessListener {
            val res = it.value
            Log.i("El dato ", "es: "+res)
            if(res == "2"){
                createSimpleNotificationLleno()
                var mapAct = mapOf(
                    "valor" to "0"
                )
                //Actualizo el valor a 0
                mDatabase.child("bandera").updateChildren(mapAct)
            }else{
                Log.e("Error","No se lanzo notificación")
            }


        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)

        }
    }

    public fun lanzaNotificacionTrafico(){

        //obtengo el valor de la bandera
        mDatabase.child("bandera").child("valor").get().addOnSuccessListener {
            val res = it.value
            Log.i("El dato ", "es: "+res)
            if(res == "3"){
                createSimpleNotificationTrafico()
                var mapAct = mapOf(
                    "valor" to "0"
                )
                //Actualizo el valor a 0
                mDatabase.child("bandera").updateChildren(mapAct)
            }else{
                Log.e("Error","No se lanzo notificación")
            }


        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)

        }
    }
}