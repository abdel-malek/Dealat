//
//  Communication.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import Alamofire
import AlamofireObjectMapper
import SwiftyJSON
import ObjectMapper
import Firebase
import FirebaseMessaging

typealias ServiceResponse = (JSON?, NSError?) -> Void

class Communication: BaseManager {
    
    static let shared = Communication()
    
    let encodingQuery = URLEncoding(destination: .queryString)
    let encodingBody = URLEncoding(destination: .httpBody)
    
//        var baseURL = "http://192.168.0.125/Dealat/index.php/api"
//        var baseImgsURL = "http://192.168.9.96/Dealat/"
    
//    let baseURL = "http://dealat.tradinos.com/index.php/api"
//    let baseImgsURL = "http://dealat.tradinos.com/"

    var baseURL = "http://www.deal-at.com/index.php/api"
    var baseImgsURL = "http://www.deal-at.com/"
    
    
    let get_latest_itemsURL = "/items_control/get_latest_items/format/json"
    let get_allURL = "/categories_control/get_all/format/json"
    let get_items_by_main_categoryURL = "/items_control/get_items_by_main_category/format/json"
    let get_item_detailsURL = "/items_control/get_item_details/format/json"
    let get_nested_categoriesURL = "/categories_control/get_nested_categories/format/json"
    let searchURL = "/items_control/search/format/json"
    let get_data_listsURL = "/items_control/get_data_lists/format/json"
    let get_countriesURL = "/users_control/get_countries/format/json"
    let post_new_itemURL = "/items_control/post_new_item/format/json"
    let edit_itemURL = "/items_control/edit/format/json"
    let get_commercial_itemsURL = "/commercial_items_control/get_commercial_items/format/json"
    let get_report_messagesURL  = "/items_control/get_report_messages/format/json"
    let report_itemURL  = "/items_control/report_item/format/json"
    
    let users_registerURL = "/users_control/register/format/json"
    let verifyURL = "/users_control/verify/format/json"
    let save_user_tokenURL = "/users_control/save_user_token/format/json"
    
    let set_as_favoriteURL = "/items_control/set_as_favorite/format/json"
    let remove_from_favoriteURL = "/items_control/remove_from_favorite/format/json"
    let get_my_favoritesURL = "/users_control/get_my_favorites/format/json"
    
    let get_my_itemsURL = "/users_control/get_my_items/format/json"
    let get_my_infoURL = "/users_control/get_my_info/format/json"
    let edit_user_infoURL = "/users_control/edit_user_info/format/json"
    let item_images_uploadURL = "/items_control/item_images_upload/format/json"
    let item_video_uploadURL = "/items_control/item_video_upload/format/json"
    
    let get_my_chat_sessionsURL = "/users_control/get_my_chat_sessions/format/json"
    let get_chat_messagesURL = "/users_control/get_chat_messages/format/json"
    let send_msgURL = "/users_control/send_msg/format/json"
    
    let mark_searchURL = "/users_control/mark_search/format/json"
    let get_my_bookmarksURL = "/users_control/get_my_bookmarks/format/json"
    let get_bookmark_searchURL = "/items_control/get_bookmark_search/format/json"
    let delete_bookmarkURL = "/users_control/delete_bookmark/format/json"
    
    let change_statusURL = "/items_control/change_status/format/json"
    let logoutURL = "/users_control/logout/format/json"
    var get_about_infoURL  = "/data_control/get_about_info/format/json"
    let delete_my_accountURL = "/users_control/delete_my_account/format/json"
    
    let QR_code_scanURL = "/QR_users_control/QR_code_scan/format/json"
    
    
    func get_latest_ads(_ callback : @escaping ([AD]) -> Void){
        let url = URL(string: baseURL + get_latest_itemsURL)!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var res = [AD]()
                    
                    for i in value.data.arrayValue{
                        let a = AD(JSON: i.dictionaryObject!)!
                        res.append(a)
                    }
                    
                    callback(res)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func get_ads_by_main_category(_ category_id : Int,page_num : Int, callback : @escaping ([AD],[Commercial]) -> Void){
        let url = URL(string: baseURL + get_items_by_main_categoryURL)!
        let params : [String : Any] = ["category_id" : category_id,"page_size" : Provider.PAGE_SIZE,"page_num" : page_num]
        
        Alamofire.request(url, method: .get, parameters: params, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var ads = [AD]()
                    var commercials = [Commercial]()
                    
                    for i in value.data["ads"].arrayValue{
                        let a = AD(JSON: i.dictionaryObject!)!
                        ads.append(a)
                    }
                    for i in value.data["commercials"].arrayValue{
                        let a = Commercial(JSON: i.dictionaryObject!)!
                        commercials.append(a)
                    }

                    callback(ads,commercials)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    func get_ad_details(ad_id : Int,template_id : Int, callback : @escaping (AD) -> Void){
        let url = URL(string: baseURL + get_item_detailsURL)!
        let params : [String : Any] = ["ad_id" : ad_id,"template_id" :template_id]
        
        Alamofire.request(url, method: .get, parameters: params, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    if let i = value.data.dictionaryObject{
                        if let a = AD(JSON: i){
                            callback(a)
                        }
                    }
                    
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    func get_data_lists(_ callback :  @escaping (_ cities : [City], _ locations : [Location], _ types : [Type], _ educations : [Education] , _ schedules : [Schedule], _ periods : [Period], _ certificates : [Certificate], _ states : [PropertyState]) -> Void){
        let url = URL(string: baseURL + get_data_listsURL)!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var cities = [City]()
                    var locations = [Location]()
                    var types = [Type]()
                    var educations = [Education]()
                    var schedules = [Schedule]()
                    var periods = [Period]()
                    var certificates = [Certificate]()
                    var states = [PropertyState]()
                    
                    
                    for i in value.data["nested_locations"].arrayValue{
                        if let obj = i.dictionaryObject, let a = City(JSON: obj){
                            cities.append(a)
                        }
                    }
                    
                    for i in value.data["location"].arrayValue{
                        if let obj = i.dictionaryObject, let a = Location(JSON: obj){
                            locations.append(a)
                        }
                    }
                    
                    
                    let tys = value.data["types"]
                    for i in 0..<11{
                        for j in tys["\(i)"].arrayValue{
                            if let obj = j.dictionaryObject, let a = Type(JSON: obj){
                                types.append(a)
                            }
                        }
                    }
                    
                    
                    for i in value.data["educations"].arrayValue{
                        if let obj = i.dictionaryObject, let a = Education(JSON: obj){
                            educations.append(a)
                        }
                    }
                    
                    for i in value.data["schedules"].arrayValue{
                        if let obj = i.dictionaryObject, let a = Schedule(JSON: obj){
                            schedules.append(a)
                        }
                    }
                    
                    for i in value.data["show_periods"].arrayValue{
                        if let obj = i.dictionaryObject, let a = Period(JSON: obj){
                            periods.append(a)
                        }
                    }
                    
                    for i in value.data["certificates"].arrayValue{
                        if let obj = i.dictionaryObject, let a = Certificate(JSON: obj){
                            certificates.append(a)
                        }
                    }
                    for i in value.data["states"].arrayValue{
                        if let obj = i.dictionaryObject, let a = PropertyState(JSON: obj){
                            states.append(a)
                        }
                    }
                    
                    
                    
                    
                    callback(cities,locations,types,educations,schedules,periods,certificates,states)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func get_countries(_ callback :  @escaping ( _ cities : [City]) -> Void){
        let url = URL(string: baseURL + get_countriesURL)!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var cities = [City]()
                    
                    for i in value.data.arrayValue{
                        if let obj = i.dictionaryObject, let a = City(JSON: obj){
                            cities.append(a)
                        }
                    }
                    
//                    Provider.shared.currency_en = value.currency_en
//                    Provider.shared.currency_ar = value.currency_ar

                    
                    callback(cities)
                    
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func get_report_messages(_ callback :  @escaping ( _ reports : [ReportMessage]) -> Void){
        let url = URL(string: baseURL + get_report_messagesURL)!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var reports = [ReportMessage]()
                    
                    for i in value.data.arrayValue{
                        if let obj = i.dictionaryObject, let a = ReportMessage(JSON: obj){
                            reports.append(a)
                        }
                    }
                    
                    callback(reports)
                    
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    func report_item(ad_id : Int,report_message_id : Int,  callback :  @escaping (CustomResponse) -> Void){
        let url = URL(string: baseURL + report_itemURL)!
        
        let params : [String : Any] = ["ad_id" : ad_id ,"report_message_id" : report_message_id ]
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    callback(value)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    
    func search(page_num : Int,query : String!,filter : FilterParams, callback : @escaping ([AD],[Commercial]) -> Void){
        let url = URL(string: baseURL + searchURL)!
        
        var params : [String : Any] = ["page_size" : Provider.PAGE_SIZE,"page_num" : page_num]
        
        
        if let q = query , !q.isEmpty{
            params["query"] = q
        }else{
            for i in FilterParams.getParams(filter){
                params[i.key] = i.value
            }
            
            FilterParams.shared = filter
        }
        
        
        Alamofire.request(url, method: .get, parameters: params, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var ads = [AD]()
                    var commercials = [Commercial]()
                    
                    for i in value.data["ads"].arrayValue{
                        let a = AD(JSON: i.dictionaryObject!)!
                        ads.append(a)
                    }
                    for i in value.data["commercials"].arrayValue{
                        let a = Commercial(JSON: i.dictionaryObject!)!
                        commercials.append(a)
                    }
                    
                    callback(ads,commercials)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func get_nested_categories(_ callback : @escaping ([Cat]) -> Void){
        let url = URL(string: baseURL + get_nested_categoriesURL)!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var res = [Cat]()
                    
                    for i in value.data.arrayValue{
                        let c = Cat(JSON: i.dictionaryObject!)!
                        res.append(c)
                    }
                    
                    callback(res)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    
    func get_all(_ callback : @escaping ( _ response : DataResponse<CustomResponse> ,_ categories : [Cat],_ commercials : [Commercial]) -> Void){
        let url = URL(string: baseURL + get_allURL)!
        print(url.absoluteString)
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var categories = [Cat]()
                    var commercials = [Commercial]()

                    for i in value.data["categories"].arrayValue{
                        let a = Cat(JSON: i.dictionaryObject!)!
                        categories.append(a)
                    }
                    for i in value.data["commercials"].arrayValue{
                        let a = Commercial(JSON: i.dictionaryObject!)!
                        commercials.append(a)
                    }

                    Provider.shared.catsFull = categories
                    let resFinal = self.loadCats(categories, i: 0)
                    Provider.shared.cats = resFinal
                    
//                    Provider.shared.currency_en = value.currency_en
//                    Provider.shared.currency_ar = value.currency_ar

                    callback(response,resFinal, commercials)
                    
                }else{
                    callback(response,[Cat](), [Commercial]())
//                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                callback(response,[Cat](), [Commercial]())
//                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func loadCats(_ res : [Cat] , i : Int) -> [Cat]{
        let catsBases = res.filter({$0.parent_id.intValue == i})
        
        for j in catsBases{
            j.children += loadCats(res,i : j.category_id.intValue)
        }
        
        return catsBases
    }
    
    
    func post_new_ad(category_id : Int,title : String,description : String,images : [String],paramsAdditional : [String: Any], _ callback : @escaping (CustomResponse) -> Void){
        
        let url = URL(string: baseURL + post_new_itemURL)!
        
        var params : [String : Any] = [:]
        for i in paramsAdditional{
            params[i.key] = i.value
        }
        params["category_id"] = category_id
        params["title"] = title
        params["description"] = description
        
        let images2 = images.filter({!$0.isEmpty})
        if !images2.isEmpty {
            if let main_image = images2.first{
                params["main_image"] = main_image
            }
            
            var imagesArray = [String]()
            for i in 1..<images2.count{
                //                if let a = images2[i].addingPercentEncoding(withAllowedCharacters: .urlHostAllowed){
                imagesArray.append(images2[i])
                //                }
            }
            
            if let yy = JSON(imagesArray).rawString(){
                print("YYYY \(yy)")
                params["images"] = yy
            }
        }
        
        
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    callback(value)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    func edit_item(ad_id : Int,category_id : Int,title : String,description : String,images : [String],paramsAdditional : [String: Any],edit_status : Int, _ callback : @escaping (CustomResponse) -> Void){
        
        let url = URL(string: baseURL + edit_itemURL)!
        
        var params : [String : Any] = [:]
        for i in paramsAdditional{
            params[i.key] = i.value
        }
        params["ad_id"] = ad_id
        params["category_id"] = category_id
        params["title"] = title
        params["description"] = description
        params["edit_status"] = edit_status
        
        let images2 = images.filter({!$0.isEmpty})
        if !images2.isEmpty {
            if let main_image = images2.first{
                params["main_image"] = main_image
            }
            
            var imagesArray = [String]()
            for i in 1..<images2.count{
                imagesArray.append(images2[i])
            }
            
            if let yy = JSON(imagesArray).rawString(){
                params["images"] = yy
            }
        }
        
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    
                    
                    callback(value)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    
    func get_commercial_ads(_ category_id : Int, callback : @escaping ([Commercial]?) -> Void){
        let url = URL(string: baseURL + get_commercial_itemsURL)!
        let params : [String : Any] = ["category_id" : category_id]
        
        Alamofire.request(url, method: .get, parameters: params, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var res = [Commercial]()
                    
                    for i in value.data.arrayValue{
                        let a = Commercial(JSON: i.dictionaryObject!)!
                        res.append(a)
                    }
                    
                    res = res.filter({$0.position.intValue == 3})
                    
                    callback(res)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                    callback(nil)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                callback(nil)
                break
            }
        }
    }
    
    
    func users_register(phone : String,name : String, callback : @escaping (Bool) -> Void){
        
        let url = URL(string: baseURL + users_registerURL)!
        
        let params : [String : Any] = ["phone" : phone,"name" : name]
        
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    Provider.addTimer(60)
                    
                    if let i = value.data, let obj = i.dictionaryObject{
                        let newUser = User.getObject(obj)
                        newUser.statues_key = User.USER_STATUES.PENDING_CODE.rawValue
                        User.saveMe(me: newUser)
                        callback(true)
                    }
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    func verify(code : String,is_multi : Int, callback : @escaping (Bool) -> Void){
        
        let url = URL(string: baseURL + verifyURL)!
        
        let phone : String = User.getCurrentUser().phone!
        let params : [String : Any] = ["phone" : phone,"verification_code" : code,"is_multi" : is_multi]
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    if let i = value.data, let obj = i.dictionaryObject{
                        let newUser = User.getObject(obj)
                        newUser.statues_key = User.USER_STATUES.PENDING_PROFILE.rawValue
                        User.saveMe(me: newUser)
                        callback(true)
                    }
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func save_user_token(_ token : String, callback : @escaping (Bool) -> Void){
        
        let url = URL(string: "http://www.deal-at.com/index.php/api" + save_user_tokenURL)!
        
        let lang = AppDelegate.isArabic() ? "ar" : "en"
        let params : [String : Any] = ["token" : token,"os" : 2,"lang" : lang]
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    let me = User.getCurrentUser()
                    me.token = token
                    User.saveMe(me: me)
                    
                    callback(true)
                    
                }else{
//                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                    callback(false)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func logout(_ callback : @escaping (Bool) -> Void){
        
        let url = URL(string: baseURL + logoutURL)!
        
        let me = User.getCurrentUser()
        let token : String! = me.token
        
        let params : [String : Any] = ["token" : token]
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    callback(true)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func set_as_favorite(_ ad_id : Int, callback : @escaping (Bool) -> Void){
        
        let url = URL(string: baseURL + set_as_favoriteURL)!
        
        let params : [String : Any] = ["ad_id" : ad_id]
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    callback(true)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    func remove_from_favorite(_ ad_id : Int, callback : @escaping (Bool) -> Void){
        
        let url = URL(string: baseURL + remove_from_favoriteURL)!
        
        let params : [String : Any] = ["ad_id" : ad_id]
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    callback(true)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func get_my_favorites(page_num : Int, callback : @escaping ([AD]) -> Void){
        let url = URL(string: baseURL + get_my_favoritesURL)!
        let params = ["page_size" : Provider.PAGE_SIZE,"page_num" : page_num]

        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var res = [AD]()
                    
                    for i in value.data.arrayValue{
                        let a = AD(JSON: i.dictionaryObject!)!
                        res.append(a)
                    }
                    
                    callback(res)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func get_my_items(page_num : Int,status : Int, callback : @escaping ([AD]) -> Void){
        let url = URL(string: baseURL + get_my_itemsURL)!
        let params = ["page_size" : Provider.PAGE_SIZE,"page_num" : page_num,"status" : status]
        
        Alamofire.request(url, method: .get, parameters: params, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var res = [AD]()
                    
                    for i in value.data.arrayValue{
                        let a = AD(JSON: i.dictionaryObject!)!
                        res.append(a)
                    }
                    
                    callback(res)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    
    func get_my_chat_sessions(_ callback : @escaping ([Chat]) -> Void){
        let url = URL(string: baseURL + get_my_chat_sessionsURL)!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var res = [Chat]()
                    
                    for i in value.data.arrayValue{
                        let a = Chat(JSON: i.dictionaryObject!)!
                        res.append(a)
                    }
                    
                    callback(res)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    
    func get_chat_messages( chat : Chat, callback : @escaping ([Message]) -> Void){
        let url = URL(string: baseURL + get_chat_messagesURL)!
        
        var params : [String : Any] = [:]
        
        if let chat_session_id = chat.chat_session_id{
            params["chat_session_id"] = chat_session_id.intValue
        }else{
            params["ad_id"] = chat.ad_id.intValue
        }
        
        Alamofire.request(url, method: .get, parameters: params, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var res = [Message]()
                    let me = User.getCurrentUser()
                    
                    for i in value.data.arrayValue{
                        let a = Message(JSON: i.dictionaryObject!)!
                        
                        
                        if let id = me.user_id, chat.seller_id.intValue == id{
                            a.to_seller = JSON(!a.to_seller.boolValue)
                        }
                        
                        res.append(a)
                    }
                    
                    callback(res)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func send_msg( ad_id : Int,chat_session_id : Int!, msg : String, callback : @escaping (Message) -> Void){
        let url = URL(string: baseURL + send_msgURL)!
        
        var params : [String : Any] = ["ad_id" : ad_id,"msg" : msg]
        
        if chat_session_id != nil {
            params["chat_session_id"] = chat_session_id
        }
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    if let i = value.data.dictionaryObject{
                        if let a = Message(JSON: i){
                            callback(a)
                        }
                    }

                    
//                    callback(true)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func get_my_info(_ callback : @escaping (User) -> Void){
        
        let url = URL(string: baseURL + get_my_infoURL)!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    if let i = value.data, let obj = i.dictionaryObject{
                        let newUser = User.getObject(obj)
                        User.saveMe(me: newUser)
                        callback(newUser)
                    }
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func edit_user_info(name : String,city_id : Int,email : String,phone : String,  callback : @escaping (Bool) -> Void){
        
        let url = URL(string: baseURL + edit_user_infoURL)!
        let params  : [String : Any] = ["name" : name, "city_id" : city_id, "email" : email, "phone" :phone]
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    if let i = value.data, let obj = i.dictionaryObject{
                        let newUser = User.getObject(obj)
                        User.saveMe(me: newUser)
                        callback(true)
                    }
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    
    func mark_search(_ callback : @escaping (Bool) -> Void){
        
        let url = URL(string: baseURL + mark_searchURL)!
        var params : [String : Any] = [:]
        
        for i in FilterParams.getParams(FilterParams.shared){
            params[i.key] = i.value
        }
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    
                    
                    callback(true)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func get_my_bookmarks(_ callback : @escaping ([UserBookmark]) -> Void){
        
        let url = URL(string: baseURL + get_my_bookmarksURL)!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var res = [UserBookmark]()
                    
                    for i in value.data.arrayValue{
                        let a = UserBookmark(JSON: i.dictionaryObject!)!
                        res.append(a)
                    }
                    
                    callback(res)
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func get_bookmark_search(page_num : Int, user_bookmark_id : Int, callback : @escaping ([AD]) -> Void){
        
        let url = URL(string: baseURL + get_bookmark_searchURL)!
        let params = ["user_bookmark_id" : user_bookmark_id,"page_size" : Provider.PAGE_SIZE,"page_num" : page_num]

        
        Alamofire.request(url, method: .get, parameters: params, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var res = [AD]()
                    
                    for i in value.data.arrayValue{
                        let a = AD(JSON: i.dictionaryObject!)!
                        res.append(a)
                    }
                    
                    callback(res)
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func delete_bookmark(user_bookmark_id : Int , callback : @escaping (CustomResponse) -> Void){
        
        let url = URL(string: baseURL + delete_bookmarkURL)!
        let params = ["user_bookmark_id" : user_bookmark_id]
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    
                    callback(value)
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func change_status(ad_id : Int,status : Int, callback : @escaping (Bool) -> Void){
        
        let url = URL(string: baseURL + change_statusURL)!
        let params = ["ad_id" : ad_id, "status" : status]
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    
                    callback(true)
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func get_about_info( _ callback : @escaping (AboutInfo) -> Void){
        
        let url = URL(string: baseURL + get_about_infoURL)!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    if let i = value.data.dictionaryObject{
                        if let a = AboutInfo(JSON: i){
                            callback(a)
                        }
                    }
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func delete_my_account(_ callback : @escaping (Bool) -> Void){
        
        let url = URL(string: baseURL + delete_my_accountURL)!
        
        Alamofire.request(url, method: .post, parameters: nil, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    callback(true)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func QR_code_scan(gen_code : String, callback : @escaping (String) -> Void){
        
        let url = URL(string: baseURL + QR_code_scanURL)!
        let params = ["gen_code" : gen_code]
        
        Alamofire.request(url, method: .post, parameters: params, encoding : encodingBody, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    
                    callback(value.data.stringValue)
                    
                }else{
                    notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    
    func output(_ res : DataResponse<CustomResponse>){
        if let urlString = res.request?.url?.absoluteString{
            print(urlString)
        }
        
        if let bod = res.request?.httpBody, let body = NSString(data: bod, encoding: String.Encoding.utf8.rawValue){
            print(body)
        }
        
        if let h = res.request?.allHTTPHeaderFields{
            print(h)
        }
        
        if let s = NSString(data: res.data!, encoding: String.Encoding.utf8.rawValue){
            print(s)
        }
    }
    
    
    func getHearders() -> [String : String]{
        
        var headers :  [String : String] = [:]
        headers["lang"] = AppDelegate.isArabic() ? "ar" : "en"
        headers["city_id"] = "\(Provider.getCity())"
        headers["Api-call"] = "1"
        headers["os"] = "2"
        
        if let version = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String {
            headers["version"] = version
        }
        if let version = Bundle.main.infoDictionary?["CFBundleVersion"] as? String {
            headers["build"] = version
        }



        
        //            let plainString = "994729458:89f2558bd4b3df00b7f9a8ee9e9df679" as NSString
        //            let plainData = plainString.data(using: String.Encoding.utf8.rawValue)
        //            let base64String = plainData?.base64EncodedString(options: NSData.Base64EncodingOptions(rawValue: 0))
        //            headers["Authorization"] = "Basic \(base64String!)"
        
        
        //        headers["Authorization"] = "Basic OTk0NzI5NDU4Ojg5ZjI1NThiZDRiM2RmMDBiN2Y5YThlZTllOWRmNjc5"
        
        if User.isRegistered() || User.getCurrentUser().statues_key == User.USER_STATUES.PENDING_PROFILE.rawValue{
            let me = User.getCurrentUser()
            if let username = me.phone, let password = me.server_key{
                let plainString = "\(username):\(password)" as NSString
                let plainData = plainString.data(using: String.Encoding.utf8.rawValue)
                let base64String = plainData?.base64EncodedString(options: NSData.Base64EncodingOptions(rawValue: 0))
                headers["Authorization"] = "Basic \(base64String!)"
            }
        }
        return headers
    }
}
