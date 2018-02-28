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
    
//    let baseURL = "http://192.168.9.53/Dealat/index.php/api"
//    let baseImgsURL = "http://192.168.9.53/Dealat/"
    
    let baseURL = "http://dealat.tradinos.com/index.php/api"
    let baseImgsURL = "http://dealat.tradinos.com/"
    
    
    let get_latest_adsURL = "/ads_control/get_latest_ads/format/json"
    let get_allURL = "/categories_control/get_all/format/json"
    let get_ads_by_main_categoryURL = "/ads_control/get_ads_by_main_category/format/json"
    let get_ad_detailsURL = "/ads_control/get_ad_details/format/json"
    let get_nested_categoriesURL = "/categories_control/get_nested_categories/format/json"
    let searchURL = "/ads_control/search/format/json"
    let get_data_listsURL = "/ads_control/get_data_lists/format/json"
    let post_new_adURL = "/ads_control/post_new_ad/format/json"
    let get_commercial_adsURL = "/commercial_ads_control/get_commercial_ads/format/json"
    
    func get_latest_ads(_ callback : @escaping ([AD]) -> Void){
        let url = URL(string: baseURL + get_latest_adsURL)!
        
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
    
    
    func get_ads_by_main_category(_ category_id : Int, callback : @escaping ([AD]) -> Void){
        let url = URL(string: baseURL + get_ads_by_main_categoryURL)!
        let params : [String : Any] = ["category_id" : category_id]
        
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
    
    func get_ad_details(ad_id : Int,template_id : Int, callback : @escaping (AD) -> Void){
        let url = URL(string: baseURL + get_ad_detailsURL)!
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
    
    func get_data_lists(_ callback :  @escaping ( _ locations : [Location], _ types : [Type], _ educations : [Education] , _ schedules : [Schedule]) -> Void){
        let url = URL(string: baseURL + get_data_listsURL)!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var locations = [Location]()
                    var types = [Type]()
                    var educations = [Education]()
                    var schedules = [Schedule]()
                    
                    for i in value.data["location"].arrayValue{
                        if let obj = i.dictionaryObject, let a = Location(JSON: obj){
                            locations.append(a)
                        }
                    }
                    
                    //                    for i in value.data["types"].arrayValue{
                    //                        if let obj = i.dictionaryObject, let a = Type(JSON: obj){
                    //                            types.append(a)
                    //                        }
                    //                    }
                    
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
                    
                    
                    callback(locations,types,educations,schedules)
                    
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
    
    
    func search(query : String!,category : Cat!,location : Location!, callback : @escaping ([AD]) -> Void){
        let url = URL(string: baseURL + searchURL)!
        
        var params : [String : Any] = [:]
        
        params["query"] = (query != nil && !query.isEmpty) ? query : nil
        params["category_id"] = (category != nil) ? category.category_id.intValue : nil
        params["location_id"] = (location != nil) ? location.location_id.intValue : nil
        
        
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
    
    
    
    func get_all(_ callback : @escaping ([Cat]) -> Void){
        let url = URL(string: baseURL + get_allURL)!
        print(url.absoluteString)
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : encodingQuery, headers: getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            self.output(response)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    var res = [Cat]()
                    
                    for i in value.data.arrayValue{
                        let a = Cat(JSON: i.dictionaryObject!)!
                        res.append(a)
                    }
                    
                    Provider.shared.catsFull = res
                    
                    let resFinal = self.loadCats(res, i: 0)
                    Provider.shared.cats = resFinal
                    
                    callback(resFinal)
                    
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
    
    
    func loadCats(_ res : [Cat] , i : Int) -> [Cat]{
        let catsBases = res.filter({$0.parent_id.intValue == i})
        
        for j in catsBases{
            j.children += loadCats(res,i : j.category_id.intValue)
        }
        
        return catsBases
    }
    
    
    func post_new_ad(category_id : Int,location_id : Int,show_period : Int,title : String,description : String,price : String,images : [String],paramsAdditional : [String: Any], _ callback : @escaping (CustomResponse) -> Void){
        
        let url = URL(string: baseURL + post_new_adURL)!
        
        var params : [String : Any] = [:]
        for i in paramsAdditional{
            params[i.key] = i.value
        }
        params["category_id"] = category_id
        params["location_id"] = location_id
        params["show_period"] = show_period
        params["title"] = title
        params["description"] = description
        params["price"] = price
        
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
    
    
    
    func get_commercial_ads(_ category_id : Int, callback : @escaping ([Commercial]!) -> Void){
        let url = URL(string: baseURL + get_commercial_adsURL)!
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
        
        //        headers["Authorization"]  = "Basic b2xhOjgyN2NjYjBlZWE4YTcwNmM0YzM0YTE2ODkxZjg0ZTdi"
        
        if User.isRegistered(){
            
            //            if let t = User.getCurrentUser().token{
            //                headers["Authorization"]  = "Bearer \(t)"
            //            }
            
        }
        
        return headers
    }
    
    
    
}

