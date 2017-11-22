package com.histudent.jwsoft.histudent.model.bean.homework;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huyg on 2017/10/27.
 */

public class HomeworkDetailBean implements Serializable{


    /**
     * id : bcee16b1-85eb-401b-bc48-298e1a60e4cb
     * logo : http://img.hitongx.com/homework/xuanlogo.png
     * contents : 哈哈哈
     * hasVoice : false
     * hasImage : false
     * hasVideo : true
     * videoId : c876efff14244355bc876cab7f4c1cdb,3f7f4d7720c1482cb060d6c685dbb095,45454db13d4d4ba7815d1323820b40ef,4a4cb5ab029341d48daf6e507ed7b687,b2f28b1cc55a46c69c2ca783f4ea5f07
     * videoCover : http://vod.hitongx.com/snapshot/c876efff14244355bc876cab7f4c1cdb00001.jpg,http://vod.hitongx.com/snapshot/3f7f4d7720c1482cb060d6c685dbb09500001.jpg,http://vod.hitongx.com/snapshot/45454db13d4d4ba7815d1323820b40ef00001.jpg,http://vod.hitongx.com/snapshot/4a4cb5ab029341d48daf6e507ed7b68700001.jpg,http://vod.hitongx.com/snapshot/b2f28b1cc55a46c69c2ca783f4ea5f0700001.jpg
     * onlyOnline : false
     * subjectId : 199f0bb0-2019-413b-8049-3cb70d71b432
     * subjectName : 6
     * ownerId : 874b1868-e0de-4f1c-9bb7-a4e2fb8bcfc1
     * ownerName : 会飞的石头
     * ownerAvatar : http://img.hitongx.com/UploadFiles/UploadAvatar/20171103/1fda759d-05d1-47ec-a623-981fe347064b.jpg@100w_100h_1e_1c_2o_50-1ci.jpg
     * classId : 6160560d-4939-4a05-a987-3a5eced05cff
     * className : 2017级2班
     * gradeName : 2017级
     * createTime : 2017-11-03 19:59:33
     * timeShow : 15分钟前
     * videoList : [{"aliVideoId":"c876efff14244355bc876cab7f4c1cdb","aliVideoCover":"http://vod.hitongx.com/snapshot/c876efff14244355bc876cab7f4c1cdb00001.jpg","aliVideoPlayAuth":"eyJTZWN1cml0eVRva2VuIjoiQ0FJUzN3SjFxNkZ0NUIyeWZTaklySWJ4SWYzQnZyVnk1UHVwZEIvODFUWURWTUplMTZPZHBEejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsck1xRk1RVkZSYVpNNUVndjg4S29Wbi9KcExGc3QySjZyOEpqc1ZPaHMxMG0xMnBzdlhKYXNEVkVma3VFNVhFTWlJNS8wMGU2TC8rY2lyWVhEN0JHSmFWaUpsaFE4MEtWdzJqRjFSdkQ4dFhJUTBRazYxOUszemRaOW1nTGlidWkzdnhDa1J2MkhCaWptOHR4cW1qL015UTV4MzFpMXYweStCM3dZSHRPY3FjYThCOU1ZMVdUc3Uxdm9oemFyR1Q2Q3BaK2psTStxQVU2cWxZNG1YcnM5cUhFa0ZOd0JpWFNaMjJsT2RpTndoa2ZLTTNOcmRacGZ6bjc1MUN0L2ZVaXA3OHhtUW1YNGdYY1Z5R0ZOLzducFNVUmJ2M2I0eGxMZXVrQVJtWGpJRFRiS3VTbWhnL2ZIY1dPRGxOZjljY01YSnFBWFF1TUdxQWRQTDVwZ2lhTTFyOUVQYmRnZmhtaTRBSjVsSHA3TWVNR1YrRGVMeVF5aDBFSWFVN2EwNDR4ckRoRzVnS3NNUWFnQUdHd0hvd3Vwclo0K2FudFY2N1VscHlUQUhiNEMrQXFUazg3ZExZTEFBb1IxYjhKODFWbTlWMitrU3hkSnNvd2NxRmxiWEs1OC9HdGxLUVozYUVJVUFVeWVwYjI1dEV3L0RaZ1ZMR2ZVQkRnbTNDdlp4WFJJdnVUcy9GVnV6d0lZOFQ4b3dGR1o3OW10NWV5NjBTbW04bUpZazQvL2ZxNmtMSWhHOGlCSDMxNGc9PSIsIkF1dGhJbmZvIjoie1wiQ2FsbGVyXCI6XCI1Mm51VkF3VFF3enlHdmtHbmJ4bzZtd3ErMkl4Wm10S0NING45c0h3dHZRPVxcclxcblwiLFwiRXhwaXJlVGltZVwiOlwiMjAxNy0xMS0wM1QxMjoxNzowNlpcIixcIk1lZGlhSWRcIjpcImM4NzZlZmZmMTQyNDQzNTViYzg3NmNhYjdmNGMxY2RiXCIsXCJTaWduYXR1cmVcIjpcIllPN3JpUUFBKzcrY2RuajhocFNKU1ZlaGhOST1cIn0iLCJWaWRlb01ldGEiOnsiU3RhdHVzIjoiTm9ybWFsIiwiVmlkZW9JZCI6ImM4NzZlZmZmMTQyNDQzNTViYzg3NmNhYjdmNGMxY2RiIiwiVGl0bGUiOiI5Y2RmMjgwMGE1NDY0OTJiOGFhYTY1NDlhNDRlMzJiMy5tcDQiLCJDb3ZlclVSTCI6Imh0dHA6Ly92b2QuaGl0b25neC5jb20vc25hcHNob3QvYzg3NmVmZmYxNDI0NDM1NWJjODc2Y2FiN2Y0YzFjZGIwMDAwMS5qcGciLCJEdXJhdGlvbiI6MS40MzN9LCJBY2Nlc3NLZXlJZCI6IlNUUy5NRURqR3VTalNTOUJ2OU0xNlZYTXI4ZjZGIiwiUGxheURvbWFpbiI6InZvZC5oaXRvbmd4LmNvbSIsIkFjY2Vzc0tleVNlY3JldCI6IjdlSkRMcFhCUm5GaFNoTWhrdUU3dzdkeW4yUmhFUldES2NETWhHTmlIQ0hQIiwiUmVnaW9uIjoiY24tc2hhbmdoYWkiLCJDdXN0b21lcklkIjoxNzM4ODI1NjI4OTM1ODI2fQ==","aliVideoPlayDuration":1},{"aliVideoId":"3f7f4d7720c1482cb060d6c685dbb095","aliVideoCover":"http://vod.hitongx.com/snapshot/3f7f4d7720c1482cb060d6c685dbb09500001.jpg","aliVideoPlayAuth":"eyJTZWN1cml0eVRva2VuIjoiQ0FJUzN3SjFxNkZ0NUIyeWZTaklySWJ4SWYzQnZyVnk1UHVwZEIvODFUWURWTUplMTZPZHBEejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsck1xRk1RVkZSYVpNNUVndjg4S29Wbi9KcExGc3QySjZyOEpqc1ZPaHMxMG0xMnBzdlhKYXNEVkVma3VFNVhFTWlJNS8wMGU2TC8rY2lyWVhEN0JHSmFWaUpsaFE4MEtWdzJqRjFSdkQ4dFhJUTBRazYxOUszemRaOW1nTGlidWkzdnhDa1J2MkhCaWptOHR4cW1qL015UTV4MzFpMXYweStCM3dZSHRPY3FjYThCOU1ZMVdUc3Uxdm9oemFyR1Q2Q3BaK2psTStxQVU2cWxZNG1YcnM5cUhFa0ZOd0JpWFNaMjJsT2RpTndoa2ZLTTNOcmRacGZ6bjc1MUN0L2ZVaXA3OHhtUW1YNGdYY1Z5R0ZOLzducFNVUmJ2M2I0eGxMZXVrQVJtWGpJRFRiS3VTbWhnL2ZIY1dPRGxOZjljY01YSnFBWFF1TUdxQWRQTDVwZ2lhTTFyOUVQYmRnZmhtaTRBSjVsSHA3TWVNR1YrRGVMeVF5aDBFSWFVN2EwNDR4ckRoRzVnS3NNUWFnQUdHd0hvd3Vwclo0K2FudFY2N1VscHlUQUhiNEMrQXFUazg3ZExZTEFBb1IxYjhKODFWbTlWMitrU3hkSnNvd2NxRmxiWEs1OC9HdGxLUVozYUVJVUFVeWVwYjI1dEV3L0RaZ1ZMR2ZVQkRnbTNDdlp4WFJJdnVUcy9GVnV6d0lZOFQ4b3dGR1o3OW10NWV5NjBTbW04bUpZazQvL2ZxNmtMSWhHOGlCSDMxNGc9PSIsIkF1dGhJbmZvIjoie1wiQ2FsbGVyXCI6XCJNeGpERm96N0xBNFYwc3dLN2lRVTA4RHNDZlZEUkdjc0hodlM0Y3F4ZVNBPVxcclxcblwiLFwiRXhwaXJlVGltZVwiOlwiMjAxNy0xMS0wM1QxMjoxNzowNlpcIixcIk1lZGlhSWRcIjpcIjNmN2Y0ZDc3MjBjMTQ4MmNiMDYwZDZjNjg1ZGJiMDk1XCIsXCJTaWduYXR1cmVcIjpcIllMWHArb3NudENDdDRHSEZ4dk9nOCsyaExnQT1cIn0iLCJWaWRlb01ldGEiOnsiU3RhdHVzIjoiTm9ybWFsIiwiVmlkZW9JZCI6IjNmN2Y0ZDc3MjBjMTQ4MmNiMDYwZDZjNjg1ZGJiMDk1IiwiVGl0bGUiOiJhN2NiMDJkYTNjNzI0YmRjODM3YzI2Mjc2MTk0YTdmMy5tcDQiLCJDb3ZlclVSTCI6Imh0dHA6Ly92b2QuaGl0b25neC5jb20vc25hcHNob3QvM2Y3ZjRkNzcyMGMxNDgyY2IwNjBkNmM2ODVkYmIwOTUwMDAwMS5qcGciLCJEdXJhdGlvbiI6MS4xfSwiQWNjZXNzS2V5SWQiOiJTVFMuTUVEakd1U2pTUzlCdjlNMTZWWE1yOGY2RiIsIlBsYXlEb21haW4iOiJ2b2QuaGl0b25neC5jb20iLCJBY2Nlc3NLZXlTZWNyZXQiOiI3ZUpETHBYQlJuRmhTaE1oa3VFN3c3ZHluMlJoRVJXREtjRE1oR05pSENIUCIsIlJlZ2lvbiI6ImNuLXNoYW5naGFpIiwiQ3VzdG9tZXJJZCI6MTczODgyNTYyODkzNTgyNn0=","aliVideoPlayDuration":1},{"aliVideoId":"45454db13d4d4ba7815d1323820b40ef","aliVideoCover":"http://vod.hitongx.com/snapshot/45454db13d4d4ba7815d1323820b40ef00001.jpg","aliVideoPlayAuth":"eyJTZWN1cml0eVRva2VuIjoiQ0FJUzN3SjFxNkZ0NUIyeWZTaklySWJ4SWYzQnZyVnk1UHVwZEIvODFUWURWTUplMTZPZHBEejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsck1xRk1RVkZSYVpNNUVndjg4S29Wbi9KcExGc3QySjZyOEpqc1ZPaHMxMG0xMnBzdlhKYXNEVkVma3VFNVhFTWlJNS8wMGU2TC8rY2lyWVhEN0JHSmFWaUpsaFE4MEtWdzJqRjFSdkQ4dFhJUTBRazYxOUszemRaOW1nTGlidWkzdnhDa1J2MkhCaWptOHR4cW1qL015UTV4MzFpMXYweStCM3dZSHRPY3FjYThCOU1ZMVdUc3Uxdm9oemFyR1Q2Q3BaK2psTStxQVU2cWxZNG1YcnM5cUhFa0ZOd0JpWFNaMjJsT2RpTndoa2ZLTTNOcmRacGZ6bjc1MUN0L2ZVaXA3OHhtUW1YNGdYY1Z5R0ZOLzducFNVUmJ2M2I0eGxMZXVrQVJtWGpJRFRiS3VTbWhnL2ZIY1dPRGxOZjljY01YSnFBWFF1TUdxQWRQTDVwZ2lhTTFyOUVQYmRnZmhtaTRBSjVsSHA3TWVNR1YrRGVMeVF5aDBFSWFVN2EwNDR4ckRoRzVnS3NNUWFnQUdHd0hvd3Vwclo0K2FudFY2N1VscHlUQUhiNEMrQXFUazg3ZExZTEFBb1IxYjhKODFWbTlWMitrU3hkSnNvd2NxRmxiWEs1OC9HdGxLUVozYUVJVUFVeWVwYjI1dEV3L0RaZ1ZMR2ZVQkRnbTNDdlp4WFJJdnVUcy9GVnV6d0lZOFQ4b3dGR1o3OW10NWV5NjBTbW04bUpZazQvL2ZxNmtMSWhHOGlCSDMxNGc9PSIsIkF1dGhJbmZvIjoie1wiQ2FsbGVyXCI6XCIzK2tKRlVxV0U4RCthblp6dUNxWjA4Y3M2US9GaTR4aTBXRzRDVXY3RGMwPVxcclxcblwiLFwiRXhwaXJlVGltZVwiOlwiMjAxNy0xMS0wM1QxMjoxNzowNlpcIixcIk1lZGlhSWRcIjpcIjQ1NDU0ZGIxM2Q0ZDRiYTc4MTVkMTMyMzgyMGI0MGVmXCIsXCJTaWduYXR1cmVcIjpcIkNzNXNlQ3liQTNjN2NBRDZET0RGdGNKaDVYRT1cIn0iLCJWaWRlb01ldGEiOnsiU3RhdHVzIjoiTm9ybWFsIiwiVmlkZW9JZCI6IjQ1NDU0ZGIxM2Q0ZDRiYTc4MTVkMTMyMzgyMGI0MGVmIiwiVGl0bGUiOiJjZmRiZjZjNWMzNWE0YWJmYjg0OWYwMjUzMzhhMWMyNS5tcDQiLCJDb3ZlclVSTCI6Imh0dHA6Ly92b2QuaGl0b25neC5jb20vc25hcHNob3QvNDU0NTRkYjEzZDRkNGJhNzgxNWQxMzIzODIwYjQwZWYwMDAwMS5qcGciLCJEdXJhdGlvbiI6MS45Mn0sIkFjY2Vzc0tleUlkIjoiU1RTLk1FRGpHdVNqU1M5QnY5TTE2VlhNcjhmNkYiLCJQbGF5RG9tYWluIjoidm9kLmhpdG9uZ3guY29tIiwiQWNjZXNzS2V5U2VjcmV0IjoiN2VKRExwWEJSbkZoU2hNaGt1RTd3N2R5bjJSaEVSV0RLY0RNaEdOaUhDSFAiLCJSZWdpb24iOiJjbi1zaGFuZ2hhaSIsIkN1c3RvbWVySWQiOjE3Mzg4MjU2Mjg5MzU4MjZ9","aliVideoPlayDuration":2},{"aliVideoId":"4a4cb5ab029341d48daf6e507ed7b687","aliVideoCover":"http://vod.hitongx.com/snapshot/4a4cb5ab029341d48daf6e507ed7b68700001.jpg","aliVideoPlayAuth":"eyJTZWN1cml0eVRva2VuIjoiQ0FJUzN3SjFxNkZ0NUIyeWZTaklySWJ4SWYzQnZyVnk1UHVwZEIvODFUWURWTUplMTZPZHBEejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsck1xRk1RVkZSYVpNNUVndjg4S29Wbi9KcExGc3QySjZyOEpqc1ZPaHMxMG0xMnBzdlhKYXNEVkVma3VFNVhFTWlJNS8wMGU2TC8rY2lyWVhEN0JHSmFWaUpsaFE4MEtWdzJqRjFSdkQ4dFhJUTBRazYxOUszemRaOW1nTGlidWkzdnhDa1J2MkhCaWptOHR4cW1qL015UTV4MzFpMXYweStCM3dZSHRPY3FjYThCOU1ZMVdUc3Uxdm9oemFyR1Q2Q3BaK2psTStxQVU2cWxZNG1YcnM5cUhFa0ZOd0JpWFNaMjJsT2RpTndoa2ZLTTNOcmRacGZ6bjc1MUN0L2ZVaXA3OHhtUW1YNGdYY1Z5R0ZOLzducFNVUmJ2M2I0eGxMZXVrQVJtWGpJRFRiS3VTbWhnL2ZIY1dPRGxOZjljY01YSnFBWFF1TUdxQWRQTDVwZ2lhTTFyOUVQYmRnZmhtaTRBSjVsSHA3TWVNR1YrRGVMeVF5aDBFSWFVN2EwNDR4ckRoRzVnS3NNUWFnQUdHd0hvd3Vwclo0K2FudFY2N1VscHlUQUhiNEMrQXFUazg3ZExZTEFBb1IxYjhKODFWbTlWMitrU3hkSnNvd2NxRmxiWEs1OC9HdGxLUVozYUVJVUFVeWVwYjI1dEV3L0RaZ1ZMR2ZVQkRnbTNDdlp4WFJJdnVUcy9GVnV6d0lZOFQ4b3dGR1o3OW10NWV5NjBTbW04bUpZazQvL2ZxNmtMSWhHOGlCSDMxNGc9PSIsIkF1dGhJbmZvIjoie1wiQ2FsbGVyXCI6XCJvK1pzaTM5WnJjK29pOHFLcTlxNGZYS2RKWGFGMDFZeGpDbVBqbVZiTjc0PVxcclxcblwiLFwiRXhwaXJlVGltZVwiOlwiMjAxNy0xMS0wM1QxMjoxNzowNlpcIixcIk1lZGlhSWRcIjpcIjRhNGNiNWFiMDI5MzQxZDQ4ZGFmNmU1MDdlZDdiNjg3XCIsXCJTaWduYXR1cmVcIjpcIkdZVm5EaC8zaEU1U3R0djkzWWQvdXprRVRKND1cIn0iLCJWaWRlb01ldGEiOnsiU3RhdHVzIjoiTm9ybWFsIiwiVmlkZW9JZCI6IjRhNGNiNWFiMDI5MzQxZDQ4ZGFmNmU1MDdlZDdiNjg3IiwiVGl0bGUiOiJjMzAxZjA0NmJhNzc0MmYyOGIwM2JiNzUyMjVkMzhkMy5tcDQiLCJDb3ZlclVSTCI6Imh0dHA6Ly92b2QuaGl0b25neC5jb20vc25hcHNob3QvNGE0Y2I1YWIwMjkzNDFkNDhkYWY2ZTUwN2VkN2I2ODcwMDAwMS5qcGciLCJEdXJhdGlvbiI6NC4xNDZ9LCJBY2Nlc3NLZXlJZCI6IlNUUy5NRURqR3VTalNTOUJ2OU0xNlZYTXI4ZjZGIiwiUGxheURvbWFpbiI6InZvZC5oaXRvbmd4LmNvbSIsIkFjY2Vzc0tleVNlY3JldCI6IjdlSkRMcFhCUm5GaFNoTWhrdUU3dzdkeW4yUmhFUldES2NETWhHTmlIQ0hQIiwiUmVnaW9uIjoiY24tc2hhbmdoYWkiLCJDdXN0b21lcklkIjoxNzM4ODI1NjI4OTM1ODI2fQ==","aliVideoPlayDuration":4},{"aliVideoId":"b2f28b1cc55a46c69c2ca783f4ea5f07","aliVideoCover":"http://vod.hitongx.com/snapshot/b2f28b1cc55a46c69c2ca783f4ea5f0700001.jpg","aliVideoPlayAuth":"eyJTZWN1cml0eVRva2VuIjoiQ0FJUzN3SjFxNkZ0NUIyeWZTaklySWJ4SWYzQnZyVnk1UHVwZEIvODFUWURWTUplMTZPZHBEejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsck1xRk1RVkZSYVpNNUVndjg4S29Wbi9KcExGc3QySjZyOEpqc1ZPaHMxMG0xMnBzdlhKYXNEVkVma3VFNVhFTWlJNS8wMGU2TC8rY2lyWVhEN0JHSmFWaUpsaFE4MEtWdzJqRjFSdkQ4dFhJUTBRazYxOUszemRaOW1nTGlidWkzdnhDa1J2MkhCaWptOHR4cW1qL015UTV4MzFpMXYweStCM3dZSHRPY3FjYThCOU1ZMVdUc3Uxdm9oemFyR1Q2Q3BaK2psTStxQVU2cWxZNG1YcnM5cUhFa0ZOd0JpWFNaMjJsT2RpTndoa2ZLTTNOcmRacGZ6bjc1MUN0L2ZVaXA3OHhtUW1YNGdYY1Z5R0ZOLzducFNVUmJ2M2I0eGxMZXVrQVJtWGpJRFRiS3VTbWhnL2ZIY1dPRGxOZjljY01YSnFBWFF1TUdxQWRQTDVwZ2lhTTFyOUVQYmRnZmhtaTRBSjVsSHA3TWVNR1YrRGVMeVF5aDBFSWFVN2EwNDR4ckRoRzVnS3NNUWFnQUdHd0hvd3Vwclo0K2FudFY2N1VscHlUQUhiNEMrQXFUazg3ZExZTEFBb1IxYjhKODFWbTlWMitrU3hkSnNvd2NxRmxiWEs1OC9HdGxLUVozYUVJVUFVeWVwYjI1dEV3L0RaZ1ZMR2ZVQkRnbTNDdlp4WFJJdnVUcy9GVnV6d0lZOFQ4b3dGR1o3OW10NWV5NjBTbW04bUpZazQvL2ZxNmtMSWhHOGlCSDMxNGc9PSIsIkF1dGhJbmZvIjoie1wiQ2FsbGVyXCI6XCJSc3lEdklMdWZaeW0raXRHWENHNlZwaVNRWE83dEI2Ymx5QzRJeXFSVnNnPVxcclxcblwiLFwiRXhwaXJlVGltZVwiOlwiMjAxNy0xMS0wM1QxMjoxNzowNlpcIixcIk1lZGlhSWRcIjpcImIyZjI4YjFjYzU1YTQ2YzY5YzJjYTc4M2Y0ZWE1ZjA3XCIsXCJTaWduYXR1cmVcIjpcInA4Y1U0cmxRczVRd0RmYUZPd0Vxa1E1MkZjVT1cIn0iLCJWaWRlb01ldGEiOnsiU3RhdHVzIjoiTm9ybWFsIiwiVmlkZW9JZCI6ImIyZjI4YjFjYzU1YTQ2YzY5YzJjYTc4M2Y0ZWE1ZjA3IiwiVGl0bGUiOiJlOGIwZjQwMzVjOTA0YmUxYjYyNDEwNjU2YzE5MTRhMi5tcDQiLCJDb3ZlclVSTCI6Imh0dHA6Ly92b2QuaGl0b25neC5jb20vc25hcHNob3QvYjJmMjhiMWNjNTVhNDZjNjljMmNhNzgzZjRlYTVmMDcwMDAwMS5qcGciLCJEdXJhdGlvbiI6MS42NjZ9LCJBY2Nlc3NLZXlJZCI6IlNUUy5NRURqR3VTalNTOUJ2OU0xNlZYTXI4ZjZGIiwiUGxheURvbWFpbiI6InZvZC5oaXRvbmd4LmNvbSIsIkFjY2Vzc0tleVNlY3JldCI6IjdlSkRMcFhCUm5GaFNoTWhrdUU3dzdkeW4yUmhFUldES2NETWhHTmlIQ0hQIiwiUmVnaW9uIjoiY24tc2hhbmdoYWkiLCJDdXN0b21lcklkIjoxNzM4ODI1NjI4OTM1ODI2fQ==","aliVideoPlayDuration":2}]
     * imgList : []
     * voiceId : 00000000-0000-0000-0000-000000000000
     * voiceLength : 0
     * isComplete : false
     */

    private String id;
    private String logo;
    private String contents;
    private boolean hasVoice;
    private boolean hasImage;
    private boolean hasVideo;
    private String videoId;
    private String videoCover;
    private boolean onlyOnline;
    private String subjectId;
    private String subjectName;
    private String ownerId;
    private String ownerName;
    private String ownerAvatar;
    private String classId;
    private String className;
    private String gradeName;
    private String createTime;
    private String timeShow;
    private String voiceId;
    private int voiceLength;
    private boolean isComplete;
    private List<VideoDetailBean> videoList;
    private List<WorkImgDetailBean> imgList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isHasVoice() {
        return hasVoice;
    }

    public void setHasVoice(boolean hasVoice) {
        this.hasVoice = hasVoice;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public boolean isOnlyOnline() {
        return onlyOnline;
    }

    public void setOnlyOnline(boolean onlyOnline) {
        this.onlyOnline = onlyOnline;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTimeShow() {
        return timeShow;
    }

    public void setTimeShow(String timeShow) {
        this.timeShow = timeShow;
    }

    public String getVoiceId() {
        return voiceId;
    }

    public void setVoiceId(String voiceId) {
        this.voiceId = voiceId;
    }

    public int getVoiceLength() {
        return voiceLength;
    }

    public void setVoiceLength(int voiceLength) {
        this.voiceLength = voiceLength;
    }

    public boolean isIsComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public List<VideoDetailBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoDetailBean> videoList) {
        this.videoList = videoList;
    }

    public List<WorkImgDetailBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<WorkImgDetailBean> imgList) {
        this.imgList = imgList;
    }


}
