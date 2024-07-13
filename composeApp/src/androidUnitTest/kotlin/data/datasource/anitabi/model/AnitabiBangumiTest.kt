package data.datasource.anitabi.model

import data.util.globalJson
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.time.measureTime

class AnitabiBangumiTest {
  @Test
  fun testJson() {
    val json = """
      [
          220188,
          "巴加的工作室",
          0,
          "バジャのスタジオ",
          "宇治市",
          "#ffae00",
          "/images/bangumi/220188.jpg",
          7.5,
          "OVA",
          34.924701,
          135.798642,
          15.873,
          1719518630461,
          [
              [
                  "5gr3r7z9n",
                  "京都动画本社",
                  "京都动画本社",
                  34.924702,
                  135.798642,
                  0,
                  0,
                  0,
                  "/images/user/0/bangumi/220188/points/5gr3r7z9n-1673561736637.jpg",
                  0,
                  0,
                  13,
                  0,
                  0,
                  0,
                  null
              ]
          ],
          0,
          [],
          0
      ]
    """.trimIndent()
    val anime = globalJson.decodeFromString<AnitabiBangumi>(json)
    assertEquals(220188, anime.id)
    assertEquals("巴加的工作室", anime.cnTitle)
    assertEquals("", anime.enTitle)
    assertEquals("バジャのスタジオ", anime.jpTitle)
    assertEquals("宇治市", anime.city)
    assertEquals("#ffae00", anime.color)
    assertEquals("/images/bangumi/220188.jpg", anime.cover)
    assertEquals(7.5f, anime.rate)
    assertEquals("OVA", anime.tag)
    assertEquals(34.924701, anime.latitude)
    assertEquals(135.798642, anime.longitude)
    assertEquals(15.873, anime.zoom)
    assertEquals(1719518630461, anime.modified)
  }

  @Test
  fun testJson2() {
    val json = """
      [
          100205,
          "新妹魔王的契约者",
          0,
          "新妹魔王の契約者",
          "东京都",
          "#e2656b",
          "/images/bangumi/100205.jpg",
          5.8,
          "TV",
          35.630769,
          139.447658,
          12.917,
          1717170383202,
          [
              [
                  "51i529dbq",
                  "小田急永山駅",
                  0,
                  35.630769,
                  139.447655,
                  0,
                  "zdM6qTIlrilQ.kPfQiEAiQ4IM",
                  0,
                  0,
                  0,
                  0,
                  0,
                  "2～3話",
                  0,
                  0,
                  "聖蹟桜ヶ丘"
              ],
              [
                  "51i529djb",
                  "FORESTY COFFEE",
                  0,
                  35.629953,
                  139.4484,
                  0,
                  "zdM6qTIlrilQ.kPfQiEAiQ4IM",
                  0,
                  0,
                  0,
                  0,
                  0,
                  "2話",
                  0,
                  0,
                  "聖蹟桜ヶ丘"
              ],
              [
                  "51i529cs4",
                  "聖蹟桜ヶ丘駅",
                  0,
                  35.650784,
                  139.447048,
                  0,
                  "zdM6qTIlrilQ.kPfQiEAiQ4IM",
                  0,
                  0,
                  0,
                  0,
                  0,
                  0,
                  0,
                  0,
                  "聖蹟桜ヶ丘"
              ],
              [
                  "51i529dbt",
                  "歩道橋",
                  0,
                  35.651481,
                  139.446512,
                  0,
                  "zdM6qTIlrilQ.kPfQiEAiQ4IM",
                  0,
                  0,
                  0,
                  0,
                  0,
                  "6話～",
                  0,
                  0,
                  "聖蹟桜ヶ丘"
              ],
              [
                  "51i529dje",
                  "焼肉",
                  0,
                  35.649145,
                  139.452676,
                  0,
                  "zdM6qTIlrilQ.kPfQiEAiQ4IM",
                  0,
                  0,
                  0,
                  0,
                  0,
                  "5話",
                  0,
                  0,
                  "聖蹟桜ヶ丘"
              ],
              [
                  "51i529cz1",
                  "大河原公園",
                  0,
                  35.65067,
                  139.453851,
                  0,
                  "zdM6qTIlrilQ.kPfQiEAiQ4IM",
                  0,
                  0,
                  0,
                  0,
                  0,
                  "5話",
                  0,
                  0,
                  "聖蹟桜ヶ丘"
              ],
              [
                  "51i529d8o",
                  "京王電鉄本社ビル",
                  0,
                  35.651329,
                  139.448009,
                  0,
                  "zdM6qTIlrilQ.kPfQiEAiQ4IM",
                  0,
                  0,
                  0,
                  0,
                  0,
                  0,
                  0,
                  0,
                  "聖蹟桜ヶ丘"
              ],
              [
                  "51i529dd7",
                  "西口タクシー乗り場",
                  0,
                  35.650457,
                  139.446609,
                  0,
                  "zdM6qTIlrilQ.kPfQiEAiQ4IM",
                  0,
                  0,
                  0,
                  0,
                  0,
                  0,
                  0,
                  0,
                  "聖蹟桜ヶ丘"
              ],
              [
                  "51i529d09",
                  "SHIBUYA109",
                  0,
                  35.659549,
                  139.699163,
                  0,
                  "zdM6qTIlrilQ.kPfQiEAiQ4IM",
                  0,
                  0,
                  0,
                  0,
                  0,
                  "6話",
                  0,
                  0,
                  "聖蹟桜ヶ丘"
              ],
              [
                  "51i529d96",
                  "旧多摩聖跡記念館",
                  0,
                  35.638002,
                  139.460631,
                  0,
                  "zdM6qTIlrilQ.kPfQiEAiQ4IM",
                  0,
                  0,
                  0,
                  0,
                  0,
                  "9話",
                  0,
                  0,
                  "聖蹟桜ヶ丘"
              ]
          ],
          0,
          0,
          0
      ]
    """.trimIndent()
    val anime = globalJson.decodeFromString<AnitabiBangumi>(json)
    assertEquals(100205, anime.id)
    assertEquals("新妹魔王的契约者", anime.cnTitle)
    assertEquals("", anime.enTitle)
    assertEquals("新妹魔王の契約者", anime.jpTitle)
    assertEquals("东京都", anime.city)
    assertEquals("#e2656b", anime.color)
    assertEquals("/images/bangumi/100205.jpg", anime.cover)
    assertEquals(5.8f, anime.rate)
    assertEquals("TV", anime.tag)
    assertEquals(35.630769, anime.latitude)
    assertEquals(139.447658, anime.longitude)
    assertEquals(12.917, anime.zoom)
    assertEquals(1717170383202, anime.modified)
    assertEquals(10, anime.points.size)
    val point = anime.points[0]
    assertEquals("51i529dbq", point.id)
    assertEquals("2～3話", point.description)
    assertEquals("聖蹟桜ヶ丘", point.point)
  }

  @Test
  fun testJson3() {
    val json = """
        [
        100444,
        "四月是你的谎言",
        0,
        "四月は君の嘘",
        "练马区",
        "#f28697",
        "/images/bangumi/100444.jpg",
        8,
        "TV",
        35.698194,
        139.618462,
        9.991,
        1720705232918,
        [
            [
                "51i51ymcf",
                "１話（～00:30）宮園かをりが猫を探すシーン",
                "石神井公园管理所",
                35.738884,
                139.597306,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51ymcf-1720017335431.jpg",
                0,
                1,
                24,
                "かをりが自販機の裏を見て黒猫を探しているシーン野球球場横にあります。また黒猫にエサを上げているシーン。\n第1话 宫园薰寻找黑猫的场景",
                0,
                0,
                null
            ],
            [
                "51i51ym7c",
                "１話（06:16)有馬公生、澤部椿、渡亮太が学校帰りにコンビニへ寄り道",
                "是政桥",
                35.653648,
                139.487957,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                "/images/user/0/bangumi/100444/points/51i51ym7c-1710126068313.jpg",
                0,
                1,
                380,
                0,
                0,
                0,
                null
            ],
            [
                "51i51ymjb",
                "宮園かをりが黒猫を発見した場所",
                0,
                35.752764,
                139.585977,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51ymjb-1719991258366.jpg",
                0,
                1,
                12,
                "桜並木がきれいなシーン",
                0,
                0,
                null
            ],
            [
                "51i51ymmi",
                "待ち合わせ場所と演奏場所になります",
                0,
                35.738277,
                139.6533,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1131,
                "/images/user/1130/bangumi/100444/points/51i51ymmi-1720018430933.jpg",
                0,
                1,
                942,
                "第1话 四人的集合场所和薰的演奏场所  *注：此区域包括薰演奏时站在其上的设施在内的全部游乐设施均已被拆除",
                0,
                0,
                null
            ],
            [
                "51i51yna1",
                "La Primeur カフェ",
                "La Primeur 咖啡馆",
                35.738466,
                139.588632,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51yna1-1719991479484.jpg",
                0,
                3,
                312,
                "カフェ「ラ・プリムール カフェ」。かをりが頼んだ「リンゴとナッツのキャラメルワッフル」もあります。公生が弾いたピアノは暖炉として置いてあります。",
                0,
                0,
                null
            ],
            [
                "51i51yn9y",
                "泉こぶし公園",
                0,
                35.748772,
                139.589834,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51yn9y-1720022019776.jpg",
                0,
                3,
                405,
                "泉こぶし公園になります。公生が猫を見つけ、ピアノを弾きたくない理由を聞く。そしてかをりが「友人A」を「伴奏者」に任命するシーン",
                0,
                0,
                null
            ],
            [
                "51i51ynk8",
                "練馬文化センター",
                "练马文化中心",
                35.739058,
                139.654166,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51ynk8-1719991574072.jpg",
                0,
                2,
                359,
                "TOWAHALL（練馬文化センター）へ二人乗りで到着するシーン",
                0,
                0,
                null
            ],
            [
                "51i51yo69",
                "練馬光が丘病院",
                "练马光之丘医院",
                35.762152,
                139.629756,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51yo69-1720022529276.jpg",
                0,
                5,
                320,
                "宮園かをりが入院する病院",
                0,
                0,
                null
            ],
            [
                "51i51yogb",
                "前通り橋",
                "前通り橋",
                35.766433,
                139.551022,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51yogb-1720020143619.jpg",
                0,
                "1",
                84,
                "OP&5話の主な舞台　飛び込むシーン",
                0,
                0,
                null
            ],
            [
                "51i51yo2h",
                "椿の試合の帰り道に公生が心配して待っていた場面",
                0,
                35.749543,
                139.582436,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51yo2h-1720022631073.jpg",
                0,
                6,
                1049,
                0,
                0,
                0,
                null
            ],
            [
                "51i51ynzu",
                "７,８,９話毎報音楽コンクールに出ることになった公生。",
                0,
                35.735401,
                139.487711,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                0,
                0,
                "と応援に来た渡、かをり、椿たちが演奏前に公生を見送る場所です。",
                0,
                0,
                null
            ],
            [
                "51i51ynu3",
                "公生とかをりが手を合わせるシーンがある公園になります。",
                0,
                35.714741,
                139.680401,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51ynu3-1720020369738.jpg",
                0,
                7,
                579,
                0,
                0,
                0,
                null
            ],
            [
                "51i51yo2d",
                "10話　公生と渡の帰り道",
                0,
                35.740236,
                139.587664,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                10,
                0,
                0,
                0,
                0,
                null
            ],
            [
                "51i51ynz5",
                "みんなで走るシーンです。",
                0,
                35.726859,
                139.586317,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51ynz5-1720020481073.jpg",
                0,
                11,
                684,
                0,
                0,
                0,
                null
            ],
            [
                "51i51yo5v",
                "11話　土稜橋　かをりが公生にコンクールの感想を聞いた場所",
                "前通り橋",
                35.766664,
                139.551003,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51yo5v-1720020550882.jpg",
                0,
                11,
                1112,
                0,
                0,
                0,
                null
            ],
            [
                "51i51ynub",
                "12話　瀬戸 紘子たちと出かけたお祭り",
                0,
                35.738231,
                139.672355,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                12,
                0,
                0,
                0,
                0,
                null
            ],
            [
                "51i51ynzz",
                "かをりの実家ケーキ屋のモデル",
                0,
                35.747013,
                139.566496,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51ynzz-1720022711557.jpg",
                0,
                12,
                502,
                0,
                0,
                0,
                null
            ],
            [
                "51i51yo09",
                "13話　かをりが入院している病院",
                0,
                35.76248,
                139.628978,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                13,
                0,
                0,
                0,
                0,
                null
            ],
            [
                "51i51yooo",
                "14話　公生が椿に進路を言うシーン",
                0,
                35.303034,
                139.522569,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                14,
                0,
                0,
                0,
                0,
                null
            ],
            [
                "51i51yp73",
                "15話　椿が先輩に振られるシーン",
                0,
                35.75693,
                139.569202,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                15,
                0,
                0,
                0,
                0,
                null
            ],
            [
                "51i51yovr",
                "15話　　相座凪が木から落ちてくるシーン",
                0,
                35.752124,
                139.585778,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                15,
                0,
                0,
                0,
                0,
                null
            ],
            [
                "51i51yoxw",
                "南田中团地",
                0,
                35.739365,
                139.617799,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51yoxw-1720020628564.jpg",
                0,
                16,
                337,
                "再度、かをりと公生が偶然会うシーン",
                0,
                0,
                null
            ],
            [
                "51i51yor8",
                "16話　公生が藍里凪を追いかけた寺",
                0,
                35.770281,
                139.416032,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                16,
                0,
                0,
                0,
                0,
                null
            ],
            [
                "51i51your",
                "凪が公生を追いかけた公園",
                0,
                35.707355,
                139.649009,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                "/images/user/0/bangumi/100444/points/51i51your-1705910082934.jpg",
                0,
                17,
                308,
                "凪が公生を追いかけた公園",
                0,
                0,
                null
            ],
            [
                "51i51yosg",
                "18話　凪と公生が連弾したコンサートホール",
                0,
                35.800942,
                139.716718,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                18,
                0,
                0,
                0,
                0,
                null
            ],
            [
                "51i51yoru",
                "19話　小さい頃の相座が階段を登ったシーン",
                0,
                35.77025,
                139.415968,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                19,
                0,
                0,
                0,
                0,
                null
            ],
            [
                "51i51yom3",
                "20話　公生と椿が帰りに寄った駄菓子屋",
                0,
                35.725844,
                139.764234,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                20,
                0,
                0,
                0,
                0,
                null
            ],
            [
                "51i51yp5c",
                "20話　公生がかをりに「君に会いたいんだ。　いけない？」と言ったシーン",
                0,
                35.752686,
                139.585955,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                20,
                0,
                0,
                0,
                0,
                null
            ],
            [
                "51i51yorr",
                "かをり「ありがとう！」のシーン",
                0,
                35.726905,
                139.586111,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                1130,
                "/images/user/1130/bangumi/100444/points/51i51yorr-1720020707757.jpg",
                0,
                22,
                1182,
                "かをり「ありがとう！」のシーン",
                0,
                0,
                null
            ],
            [
                "59p7s6u8p",
                "البحر الميت ים המלח",
                "死海",
                31.539761,
                35.491505,
                0,
                0,
                1000,
                "/images/user/1000/bangumi/100444/points/59p7s6u8p-1673008972460.jpg",
                0,
                1,
                102,
                "二次元横店",
                0,
                0,
                null
            ],
            [
                "idv5lhxqs",
                "明大前",
                0,
                35.668241,
                139.650702,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                null
            ],
            [
                "lxn4vdr03",
                "練馬区立大泉中学校 大泉学園通り",
                "大泉中学校",
                35.753077,
                139.586045,
                0,
                0,
                1130,
                "/images/user/1130/bangumi/100444/points/lxn4vdr03-1720023053137.jpg",
                0,
                "1",
                688,
                0,
                0,
                0,
                null
            ],
            [
                "lxncr817u",
                "前通り橋",
                "前通り橋",
                35.766576,
                139.551077,
                0,
                0,
                1130,
                "/images/user/1130/bangumi/100444/points/lxncr817u-1720023484970.jpg",
                0,
                5,
                993,
                "第5话 出院的薰等待公生的场景",
                0,
                0,
                null
            ],
            [
                "lxmsprhgc",
                "南田中団地",
                "南田中团地",
                35.739315,
                139.617305,
                0,
                0,
                1130,
                "/images/user/1130/bangumi/100444/points/lxmsprhgc-1720022212695.jpg",
                0,
                2,
                1179,
                "薰第一次称公生为“友人A”的樱花树下",
                0,
                0,
                null
            ],
            [
                "m5apj708r",
                "平成つつじ公園",
                "练马文化中心公园",
                35.739097,
                139.653024,
                0,
                "zlZiOItqQZ24.k0wqLHNw-v_Q",
                0,
                "/images/user/0/bangumi/100444/points/m5apj708r-1720623010082.jpg",
                0,
                1,
                901,
                0,
                0,
                0,
                null
            ]
        ],
        0,
        0,
        [
            "/images/ptheme/100444.webp?v=h2ghw",
            [
                "51i51ymcf",
                "51i51ym7c",
                "51i51ymjb",
                "51i51ymmi",
                "51i51yna1",
                "51i51yn9y",
                "51i51ynk8",
                "51i51yo69",
                "51i51yogb",
                "51i51yo2h",
                "51i51ynu3",
                "51i51ynz5",
                "51i51yo5v",
                "51i51ynzz",
                "51i51yoxw",
                "51i51your",
                "51i51yorr",
                "59p7s6u8p",
                "lxn4vdr03",
                "lxncr817u",
                "lxmsprhgc"
            ],
            1720089869629
        ]
    ]
    """.trimIndent()
    val anime = globalJson.decodeFromString<AnitabiBangumi>(json)
    assertEquals(100444, anime.id)
    assertEquals("四月是你的谎言", anime.cnTitle)
    assertEquals("", anime.enTitle)
    assertEquals("四月は君の嘘", anime.jpTitle)
    assertEquals("练马区", anime.city)
    assertEquals("#f28697", anime.color)
    assertEquals("/images/bangumi/100444.jpg", anime.cover)
    assertEquals(8f, anime.rate)
    assertEquals("TV", anime.tag)
    assertEquals(35.698194, anime.latitude)
    assertEquals(139.618462, anime.longitude)
    assertEquals(9.991, anime.zoom)
    assertEquals(1720705232918, anime.modified)
    assertEquals(35, anime.points.size)
    val point = anime.points[0]
    assertEquals("51i51ymcf", point.id)
    assertEquals("１話（～00:30）宮園かをりが猫を探すシーン", point.shortDescription)
    assertEquals("石神井公园管理所", point.title)
    val images = anime.images
    assertEquals("/images/ptheme/100444.webp?v=h2ghw", images.path)
    assertEquals(21, images.ids.size)
    assertEquals("51i51ymcf", images.ids.first())
    assertEquals(1720089869629, images.modified)
  }

  @OptIn(ExperimentalSerializationApi::class)
  @Test
  fun testGeoJson() {
    val jsonFile = File("src/androidUnitTest/resources/json/g.json")
    val cost = measureTime {
      val list = globalJson.decodeFromStream<List<AnitabiBangumi>>(jsonFile.inputStream())
      assertEquals(608, list.size)
    }
    println("testGeoJson cost: $cost")
  }
}
