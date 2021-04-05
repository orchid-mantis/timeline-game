(ns btg.app.ui.components.svg-images)

(defn inline-svg [{:keys [id path path-id view-box]}]
  [:svg
   {:style {:display :none}}
   [:symbol
    {:id id
     :viewBox view-box}
    [:path
     {:id path-id
      :fill "none"
      :stroke "black"
      :stroke-width 1
      :d path}]]])

(defn inline-svg-clip [{:keys [id path width height]}]
  [:svg
   {:width 0
    :height 0}
   [:defs
    [:clipPath
     {:id id
      :clipPathUnits "objectBoundingBox"
      :transform (str "scale(" (/ 1 width) (/ 1 height) ")")}
     [:path
      {:fill "none"
       :stroke "black"
       :stroke-width "1"
       :d path}]]]])

(def card-back-path "m311,754.07277c-1.925,-0.46041,-54.575,-1.34182,-117,-1.9587c-149.893,-1.481,-155.495,-1.627,-164.606,-4.274c-10.122,-2.941,-13.872,-7.015,-19.178,-20.84c-6.302,-16.419,-6.27,-16.18,-5.638,-42c0.473,-19.368,0.293,-24.819,-1.027,-31c-3.748,-17.562,-3.841,-20.008,-1.154,-30.587c2.184,-8.601,2.315,-10.07,1.106,-12.407c-1.225,-2.369,-1.013,-4.029,1.983,-15.579c1.845,-7.11,3.823,-14.502,4.395,-16.427c1.075,-3.614,5.25,-41.236,7.214,-65c1.684,-20.381,2.426,-140.674,0.915,-148.354c-0.662,-3.367,-3.825,-12.038,-7.028,-19.269c-3.204,-7.231,-6.311,-14.886,-6.906,-17.012c-0.594,-2.126,-1.61,-16.424,-2.257,-31.775c-1.242,-29.48,-0.362,-72.575,2.686,-131.59c0.782,-15.125,1.945,-45.725,2.586,-68c0.927,-32.253,1.453,-40.832,2.582,-42.129c0.78,-0.896,4.096,-2.156,7.37,-2.8c3.275,-0.644,9.982,-3.188,14.906,-5.653c10.793,-5.403,22.639,-16.04,30.745,-27.608c2.969,-4.238,6.165,-7.944,7.102,-8.237c7.049,-2.201,54.301,-7.52,86.704,-9.76c38.53024,-2.66316224,168.21162,-1.13364532,194.84981,2.2981409c17.0157,2.192124,36.43207,9.5489578,60.94947,23.0936618c23.36817,12.909806,30.68319,21.121383,34.11385,38.294941c2.44164,12.222595,3.94182,81.20181,2.70766,124.5c-3.10773,109.02941,-3.46299,132.62198,-2.39422,159c1.03896,25.64232,1.20993,27.09876,5.28232,45l4.20861,18.5l-0.54848,61.5c-0.54436,61.03883,-1.12881,75.35122,-4.28003,104.81253c-1.50758,14.09465,-1.51014,15.96372,-0.0321,23.5c5.50466,28.06757,7.46827,56.72184,5.76936,84.19029c-1.53012,24.73925,-4.61245,46.39675,-6.92581,48.66295c-0.93524,0.91617,-6.73676,4.42166,-12.89228,7.78997c-14.80912,8.10359,-17.91053,8.5665,-66.80815,9.9718c-21.45,0.61646,-42.825,1.31467,-47.5,1.55159c-4.675,0.23691,-10.075,0.054,-12,-0.40636zm58,-13.08668c47.02583,-1.31588,49.64314,-1.66686,62.17039,-8.33705l9.67039,-5.14904l1.07245,-7.5c2.85051,-19.93478,3.47132,-29.38983,3.44711,-52.5c-0.0272,-25.97699,-1.16396,-38.76931,-5.44081,-61.22796l-2.08862,-10.96782l-14.16545,-4.20063l-14.16546,-4.20063l-188,0.40139c-186.639807,0.39849,-200.62011,0.65392,-202.502662,3.69996c-0.352929,0.57105,-1.444937,4.44547,-2.426683,8.60981c-1.305182,5.53629,-1.487383,7.86915,-0.677824,8.67871c1.648938,1.64894,1.37021,4.80704,-1.382788,15.66755l-2.489957,9.82282l2.577574,13.8584c2.387682,12.83744,2.532223,15.22131,1.96198,32.3584c-0.909741,27.33975,-0.926777,27.15004,3.496134,38.93121c5.010806,13.34713,6.35957,15.15433,12.901691,17.28697c6.730436,2.19402,17.208445,2.44596,162.542535,3.90826c62.15,0.62533,114.8,1.49683,117,1.93667c2.2,0.43983,7.375,0.61354,11.5,0.386c4.125,-0.22753,24.375,-0.88589,45,-1.46302zm70.865,-170.736c2.28793,-24.68474,3.064,-45.58565,3.69101,-99.4054c0.70226,-60.27798,1.28956,-52.07982,-6.07808,-84.8446c-2.48617,-11.05632,-2.60822,-13.10112,-3.16351,-53c-0.40794,-29.31167,-0.057,-58.53429,1.19501,-99.5c1.86753,-61.10727,1.58559,-147.510309,-0.52827,-161.892077c-2.023,-13.765,-7.161,-20.706,-21.629,-29.221c-21.51,-12.659,-46.073,-22.98,-60.352,-25.357c-8.61882,-1.435312,-37.65264,-2.781038,-86.5,-4.0093c-62.62111,-1.574601,-117.57974,0.60706,-165.46632,6.568423c-26.910097,3.350017,-24.992867,2.788616,-28.882589,8.45736c-11.273436,16.42951,-29.069362,30.077731,-46.24336,35.465397l-5.592269,1.754355l-0.636827,28.617098c-0.35,15.739,-1.641,47.742,-2.869,71.117c-2.690783,51.2377,-3.312157,152.0626,-0.985046,159.83478c0.713846,2.38413,3.724933,9.80913,6.691306,16.5c8.344545,18.82173,8.226699,17.97355,9.004106,64.8061c0.989679,59.62027,-0.927022,104.49188,-6.547693,153.28711c-1.072455,9.31039,-1.751361,17.12655,-1.50868,17.36923c0.242681,0.24268,6.096086,-0.0624,13.007566,-0.67799c8.72034,-0.77668,68.186059,-1.11124,194.297439,-1.09313l181.731,0.026l12.5,3.891c6.875,2.14017,12.76618,3.92843,13.0915,3.97391c0.32533,0.0455,1.12329,-5.65481,1.77325,-12.66731z")
(def card-front-path "m311.5,754.06426c-1.65,-0.47025,-52.95,-1.35322,-114,-1.96215c-151.807,-1.514,-159.309,-1.703,-168,-4.238c-9.942,-2.899,-14.261,-7.314,-18.82,-19.24c-7.265,-19.005,-7.281,-19.135,-6.138,-48.795c0.604,-15.662,0.447,-18.789,-1.377,-27.5c-3.461,-16.532,-3.508,-17.946,-0.95,-28.829c1.9835736,-8.43975,2.1542585,-10.40279,1.0939591,-12.58157c-1.075334,-2.20968,-0.8282835,-4.29852,1.714941,-14.5c1.634,-6.555,3.475,-13.044,4.092,-14.419c0.616,-1.375,2.198,-11.95,3.514,-23.5c5.159,-45.268,5.585,-53.856,6.081,-122.5c0.541,-74.92,0.891,-71.02,-8.131,-90.737c-3.042,-6.647,-5.982,-14.038,-6.535,-16.424c-0.553,-2.386,-1.54,-16.656,-2.193,-31.711c-1.261,-29.049,-0.353,-73.592,2.661,-130.628c0.785,-14.85,1.954,-45.45,2.597,-68c0.926,-32.5,1.46,-41.337,2.575,-42.628c0.773,-0.896,4.084,-2.156,7.359,-2.8c3.274,-0.644,9.981,-3.188,14.905,-5.653c10.794,-5.403,22.639,-16.04,30.745,-27.609c2.969,-4.237,6.165,-7.944,7.102,-8.236c7.049,-2.202,54.301,-7.521,86.704,-9.76c38.53024,-2.66316224,168.21162,-1.13364532,194.84981,2.2981409c17.0157,2.192124,36.43207,9.5489578,60.94947,23.0936618c23.12113,12.773328,30.36586,20.80491,34.0922,37.794941c2.51032,11.445629,4.0273,81.1576,2.74185,126c-3.12652,109.06769,-3.46325,131.4939,-2.37235,158c1.05144,25.54749,1.24112,27.16496,5.2772,45l4.18656,18.5l-0.55,62c-0.54216,61.11556,-1.07092,74.16323,-4.22428,104.23729l-1.59768,15.23729l2.53208,14.76271c4.82053,28.10505,6.27771,52.59419,4.65688,78.26271c-1.16514,18.45193,-4.47572,44.07387,-6.08271,47.07656c-0.52372,0.97857,-6.43919,4.79691,-13.14549,8.4852c-11.00993,6.05517,-13.07254,6.82919,-21.2534,7.9756c-4.98308,0.6983,-26.38514,1.77197,-47.56014,2.38594c-21.175,0.61398,-42.1,1.31424,-46.5,1.55614c-4.4,0.2419,-9.35,0.0551,-11,-0.41518zm56.5,-13.098c48.62293,-1.37101,50.88204,-1.6684,62.95971,-8.28816c10.93147,-5.99152,9.9029,-3.82413,12.66048,-26.67791c1.22753,-10.17329,1.74028,-21.66145,1.71839,-38.5c-0.0325,-25.03518,-1.29986,-39.09852,-5.50672,-61.10822l-2.123,-11.108l-13.822,-4.142l-13.821,-4.142h-19.0327c-21.98126,0,-19.86621,-1.47176,-17.889,12.4481c1.9804,13.9423,-1.62516,23.64679,-10.48853,28.23021c-4.44797,2.30014,-14.15738,2.11712,-19.65518,-0.3705c-9.72484,-4.40025,-11.85791,-10.8257,-8.06953,-24.30781l2.10746,-7.5l-2.00674,-1.65039c-4.39382,-3.61359,-22.67599,-3.51551,-40.91948,0.21953c-14.71326,3.01228,-33.46069,9.74611,-61.11171,21.95053c-51.33164,22.6564,-69.44153,27.7635,-94.5,26.64959c-14.04747,-0.62444,-24.27001,-3.07275,-36.26325,-8.68509c-17.997,-8.421,-19.212,-11.127,-16.951,-37.734l0.78593,-9.25h-18.61384c-17.891732,0,-39.777052,1.36672,-45.866249,2.86432c-2.67956,0.65901,-3.05538,1.33888,-4.779736,8.64657c-1.483786,6.28818,-1.637521,8.49621,-0.742065,10.65804c0.920851,2.22313,0.643375,4.75955,-1.502413,13.73361l-2.631858,11.00689l2.53215,13.70805c2.78403,15.07163,3.527953,39.54219,1.452667,47.7839c-0.75509,2.99873,-0.770446,5.95875,-0.0511,9.85005c1.36835,7.40209,7.861697,24.7215,10.241682,27.31718c1.043192,1.13773,4.257455,2.80492,7.142807,3.70486c6.596424,2.05741,19.982598,2.3678,165.746094,3.84325c60.775,0.61518,112.075,1.48433,114,1.93146c1.925,0.44712,6.65,0.62447,10.5,0.39411c3.85,-0.23036,23.875,-0.89466,44.5,-1.47622zm-203,-108.509c12.89072,-2.43685,34.96954,-10.17507,60.5,-21.20414c39.80698,-17.19647,57.63112,-23.40029,77,-26.8004c12.7663,-2.24105,33.97659,-2.26822,45,-0.0576c10.28612,2.06274,13.20611,2.03314,14.34249,-0.14537c1.22226,-2.34313,2.09072,-13.68977,2.36897,-30.95117c0.22155,-13.74444,0.15216,-14.28287,-2.15754,-16.74143c-1.31255,-1.39715,-5.915,-3.90102,-10.22767,-5.56416c-12.89558,-4.97306,-20.74036,-6.3237,-36.82625,-6.34039c-24.72242,-0.0256,-41.83155,4.7937,-90.12867,25.38769c-31.24457,13.32275,-49.18417,19.77189,-64.37133,23.14098c-13.61646,3.02064,-38.44188,3.20985,-52.79819,0.40239c-13.657003,-2.6707,-12.858337,-4.00676,-13.823637,23.12508c-0.884852,24.87068,-0.922284,24.71489,6.889817,28.67322c6.44369,3.26497,20.34106,7.49677,28.23201,8.59676c8.564,1.19381,25.40065,0.48226,36,-1.52143zm194.31574,-12.5825c1.20134,-0.61873,3.19676,-2.04717,4.43426,-3.17432c3.84992,-3.50659,2.57038,-11.38584,-2.61038,-16.07436c-3.25844,-2.94885,-4.98932,-1.46583,-5.33555,4.57151l-0.304,5.302l-6.55457,-0.33794c-5.19219,-0.26771,-6.84295,0.01,-7.94201,1.33382c-1.73155,2.08639,-0.61312,4.12064,3.98829,7.25412c3.65462,2.48873,10.61482,3.0354,14.32403,1.12504zm-9.95078,-18.38372c0.711,-1.852,-0.943,-6.491,-2.315,-6.491c-1.35744,0,-4.28098,5.81795,-3.53228,7.02937c0.94301,1.52582,5.20534,1.13358,5.84681,-0.53805zm90.067,-25.741c2.08567,-22.66827,2.6192,-33.02794,3.61794,-70.25c0.60506,-22.55,0.92434,-53.15,0.70951,-68l-0.39061,-27l-3.71507,-15.5c-2.68604,-11.20668,-3.97765,-19.37784,-4.66307,-29.5c-1.43842,-21.24263,-1.17536,-77.82312,0.61959,-133.26184c1.76163,-54.40958,1.38023,-149.059329,-0.64971,-161.232139c-2.948,-17.679,-10.71,-24.598,-46.961,-41.858c-22.382,-10.657,-31.333,-12.717,-61.5,-14.153c-91.619,-4.364,-165.627,-2.856,-225.466,4.593c-26.91,3.35,-24.993,2.789,-28.882,8.458c-11.376,16.578,-28.908,30.024,-46.243,35.464l-5.591467,1.754809l-0.671591,30.11735c-0.369,16.565,-1.593,47.893,-2.718,69.618c-2.36179,45.58308,-3.5506,126.01384,-2.166413,146.57203c0.881144,13.0869,1.210946,14.66203,4.711084,22.5c2.069987,4.63538,5.371366,12.47797,7.336398,17.42797l3.572786,9l0.989008,37c0.716382,26.80069,0.656331,47.19931,-0.217844,74c-1.324383,40.60326,-2.069543,51.53302,-5.688226,83.43304c-1.33723,11.78817,-2.246157,21.6182,-2.019837,21.84452c0.226319,0.22632,5.808893,-0.081,12.40572,-0.68303c6.597,-0.602,20.368,-1.095,30.603,-1.095c15.702043,0,18.70013,-0.23671,19.190729,-1.51519c0.319789,-0.83336,0.02713,-5.22086,-0.650354,-9.75c-2.82457,-18.88293,0.10607,-28.04482,10.50615,-32.84477c7.91423,-3.65265,18.35574,-1.96243,24.75564,4.00734c3.40446,3.17564,4.06828,4.47075,4.44292,8.66804c0.3015,3.37789,-0.21241,6.79789,-1.62906,10.84113c-2.89936,8.27504,-2.72877,9.42004,1.67775,11.2612c3.03836,1.26951,6.49082,1.4687,18.25,1.05292c21.74366,-0.76881,36.17027,-4.8958,75.00275,-21.45592c43.0613,-18.36349,62.16752,-24.69889,82.5,-27.35602c12.79984,-1.67274,30.71174,-0.60175,42.19964,2.5232c10.64857,2.89663,21.72341,7.68337,25.12499,10.85947c5.17313,4.83021,5.50915,6.66466,4.74888,25.92529l-0.702,17.783l20.31423,0.0261l20.31422,0.0261l12.5,3.89121c6.875,2.14017,12.78463,3.92843,13.13251,3.97391c0.34788,0.0455,0.93269,-3.17981,1.29958,-7.16731zm-326.017,-15.99c1.07463,-3.05569,0.98181,-3.2355,-2.17044,-4.20452c-2.68589,-0.82566,-3.78969,1.13863,-2.81719,5.0134c0.83539,3.32844,3.69576,2.86456,4.98763,-0.80888zm-11.975,-3.01c0.29219,-1.7875,0.65008,-4.6,0.79531,-6.25c0.28379,-3.22414,0.25394,-3.21708,8.30228,-1.96477c3.15793,0.49137,4.56387,0.20269,6.44962,-1.3243c2.33629,-1.89181,2.35817,-2.03391,0.70237,-4.56098c-2.7941,-4.26433,-7.13465,-5.91832,-14.32923,-5.4602c-5.267399,0.3354,-6.817782,0.87371,-9.262727,3.21612c-3.807585,3.6479,-4.499342,6.69843,-2.567464,11.32206c3.391459,8.11691,8.943981,10.93079,9.909841,5.02207z")
(def scroll-background "M131.76,634.18c-4.64,-0.45,-10.39,-1.73,-17.48,-3.88c-5.57,-1.69,-9.58,-3.21,-13.53,-5.15c-7.52,-3.69,-7.61,-4.09,-6.8,-27.71c0.69,-19.88,1.01,-22.44,2.98,-24.17c1.39,-1.22,2.26,-1.21,9.02,0.09c9.81,1.9,21.59,2.66,33.62,2.18c13.18,-0.54,19.53,-1.54,31.3,-4.93c13.3,-3.83,26.92,-9,56.14,-21.32c37.69,-15.88,53.5,-21.16,70.07,-23.4c6.69,-0.9,10.92,-1.09,20.73,-0.97c13.02,0.17,18.34,0.92,27.27,3.85c10.98,3.6,16.87,6.8,18.39,10.01c0.46,0.98,0.49,1.42,0.56,9.02c0.13,13.65,-0.63,29.01,-1.69,34.18c-0.45,2.15,-0.87,2.86,-2,3.38c-1.62,0.73,-3.69,0.59,-11.45,-0.83c-5.8,-1.05,-9.31,-1.42,-16.72,-1.74c-12.06,-0.52,-24.11,0.34,-34.52,2.46c-17.15,3.51,-32.94,9.11,-69.11,24.54c-20.47,8.74,-32.48,13.41,-44.41,17.3c-16.95,5.52,-25.73,7.02,-42.36,7.2c-4.33,0.05,-8.83,0,-10.01,-0.11c0,0,0,0,0,0zm213.95,-31.21c-1.01,-0.06,-1.78,-0.37,-2.14,-0.87c-0.13,-0.19,-0.17,-0.35,-0.16,-0.7c0.01,-0.22,0.02,-0.38,0.08,-0.64c0.29,-1.38,1.29,-3.48,2.26,-4.73c0.44,-0.56,0.82,-0.9,1.14,-1c0.23,-0.07,0.51,0.02,0.78,0.27c0.98,0.85,1.9,3.43,1.86,5.18c-0.01,0.43,-0.07,0.73,-0.18,1.02c-0.28,0.73,-1.34,1.29,-2.74,1.45c-0.23,0.02,-0.67,0.04,-0.9,0.02c0,0,0,0,0,0zm6.94,18.01c-2.04,-0.14,-3.64,-0.45,-5.21,-1.03c-1.34,-0.49,-2.1,-0.93,-3.69,-2.11c-0.82,-0.61,-1.36,-1.07,-1.93,-1.64c-0.87,-0.88,-1.3,-1.53,-1.5,-2.28c-0.1,-0.35,-0.09,-0.83,0.02,-1.18c0.24,-0.81,0.87,-1.6,1.6,-2c0.87,-0.48,1.97,-0.65,4.13,-0.65c1.29,0.01,1.29,0.01,5.88,0.24c1.84,0.1,3.39,0.17,3.45,0.17c0,0,0.12,-0.01,0.12,-0.01c0,0,0.16,-2.81,0.16,-2.81c0.18,-3.13,0.2,-3.4,0.29,-4.2c0.43,-3.41,1.49,-4.79,3.19,-4.15c1.27,0.48,3.07,2.16,4.37,4.1c0.52,0.78,1.15,1.96,1.48,2.81c0.88,2.24,1.17,4.55,0.8,6.52c-0.26,1.44,-0.88,2.69,-1.78,3.63c-0.79,0.82,-2.71,2.28,-4.09,3.11c-0.44,0.26,-1.12,0.6,-1.5,0.74c-1.08,0.4,-2.28,0.63,-3.71,0.73c-0.37,0.03,-1.76,0.03,-2.08,0.01c0,0,0,0,0,0zm-242.3,-58.18c-0.27,-0.03,-0.56,-0.14,-0.79,-0.3c-0.14,-0.1,-0.37,-0.33,-0.48,-0.48c-0.29,-0.38,-0.5,-0.87,-0.65,-1.48c-0.16,-0.64,-0.27,-1.26,-0.32,-1.86c-0.03,-0.29,-0.03,-0.84,0,-1.09c0.08,-0.85,0.35,-1.45,0.81,-1.82c0.51,-0.42,1.26,-0.5,2.18,-0.25c0.17,0.05,0.76,0.23,1.05,0.33c1.33,0.44,1.77,0.77,1.86,1.35c0.03,0.15,0.02,0.42,-0.02,0.62c-0.07,0.41,-0.29,1.12,-0.59,1.97c-0.29,0.78,-0.61,1.39,-1.03,1.91c-0.13,0.16,-0.43,0.47,-0.57,0.58c-0.32,0.25,-0.65,0.42,-0.96,0.49c-0.17,0.03,-0.35,0.05,-0.49,0.03c0,0,0,0,0,0zm-11.88,-2.95c-1.52,-0.27,-3.28,-1.79,-4.86,-4.19c-0.45,-0.69,-0.77,-1.24,-1.18,-2.04c-1.13,-2.22,-1.73,-4,-1.89,-5.58c-0.04,-0.38,-0.04,-1.16,0,-1.51c0.22,-1.98,1.23,-3.78,3.26,-5.8c2.38,-2.37,3.91,-3.03,7.79,-3.39c2.59,-0.23,4.67,-0.27,6.41,-0.1c3.47,0.34,6.09,1.45,8.07,3.44c0.72,0.72,1.31,1.48,2.07,2.71c0.5,0.79,0.68,1.23,0.68,1.61c0,0.52,-0.29,0.9,-1.36,1.8c-0.29,0.24,-0.67,0.54,-0.85,0.67c-1.51,1.14,-2.82,1.44,-5.1,1.18c-0.21,-0.02,-1.09,-0.15,-1.96,-0.28c-3.46,-0.53,-4.03,-0.6,-5.09,-0.6c-0.65,0,-0.81,0.01,-0.98,0.05c-0.31,0.08,-0.53,0.2,-0.7,0.38c-0.32,0.35,-0.41,0.72,-0.56,2.28c-0.23,2.43,-0.7,5.95,-0.92,6.88c-0.32,1.31,-0.94,2.15,-1.79,2.43c-0.18,0.05,-0.29,0.07,-0.57,0.08c-0.19,0,-0.4,-0.01,-0.47,-0.02c0,0,0,0,0,0z")

(def card-border-clip-path "M256,753.88C226.57,753.44,164.93,752.64,119,752.1C119,752.1,35.5,751.12,35.5,751.12C35.5,751.12,27.91,748.72,27.91,748.72C17.67,745.48,14.4,741.87,9.11,728C2.3,710.13,2.32,710.28,3.52,681.5C4.12,667.09,3.96,663.2,2.44,656C-0.27,643.11,-0.51,630.82,1.78,621.87C3.46,615.31,3.52,614.16,2.25,612.25C0.96,610.31,1.15,608.72,4.01,597.29C5.77,590.26,7.67,583.63,8.24,582.57C8.8,581.51,10.34,571.16,11.64,559.57C16.81,513.81,17.22,505.61,17.72,436.43C18.27,361.43,18.68,366.1,9.95,347.09C7.1,340.87,4.14,333.46,3.39,330.64C2.57,327.58,1.54,314.36,0.85,298C-0.38,268.95,0.49,226.48,3.51,168C4.29,152.88,5.46,122.05,6.11,99.5C7.06,66.49,7.57,58.17,8.74,56.82C9.53,55.9,12.05,54.84,14.34,54.47C30.49,51.86,49.14,38.6,61.64,20.85C64.6,16.65,67.69,12.95,68.5,12.64C73.74,10.63,119.26,5.48,155.5,2.81C191.54,0.15,323.97,1.71,350.35,5.11C367.37,7.3,386.78,14.66,411.3,28.21C434.03,40.76,440.81,48.03,444.89,64.18C447.86,75.91,449.57,143.53,448.14,192.5C445.13,295.37,444.7,323.61,445.75,349.5C446.76,374.37,446.99,376.33,451,394.5C451,394.5,455.19,413.5,455.19,413.5C455.19,413.5,454.66,475.5,454.66,475.5C454.14,536.61,453.63,549.17,450.41,580.23C448.81,595.62,448.82,596.2,450.89,607.23C457.75,643.85,458.26,685.01,452.33,723.5C451.48,729,450.39,734.26,449.9,735.18C448.57,737.68,425.72,749.91,420.25,751.05C414.17,752.31,347.62,754.96,326,754.8C316.93,754.73,285.43,754.32,256,753.88Z")

(defn card-front []
  [:svg
   {:style {:display :none}}
   [:symbol
    {:id "card-front"
     :viewBox "0 0 458 755"
     :preserveAspectRatio "xMidYMid meet"}
    [:path
     {:fill "none"
      :stroke "black"
      :stroke-width 1
      :d card-front-path}]
    [:path
     {:fill "#ffffff"
      :stroke "#ffffff"
      :stroke-width 1
      :fill-opacity 1
      :style {:fill "var(--primary-color)"}
      :d scroll-background}]]])

(defn card-border-clip []
  [inline-svg-clip {:id "card-border-clip"
                    :path card-border-clip-path
                    :width 458
                    :height 755}])

(defn card-back []
  [inline-svg {:id "card-back"
               :path card-back-path
               :view-box "0 0 458 755"}])