//
//  Communication.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 2/5/18.
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
    
    let baseURL = "http://192.168.9.53/Dealat/index.php/api"

    let get_latest_adsURL = "/ads_control/get_latest_ads/format/json"
    let get_ads_by_main_categoryURL = "/ads_control/get_ads_by_main_category/format/json"
    let get_ad_detailsURL = "/ads_control/get_ad_details/format/json"
    let get_nested_categoriesURL = "/categories_control/get_nested_categories/format/json"

    
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
        
        if User.isRegistered(){
            
//            if let t = User.getCurrentUser().token{
//                headers["Authorization"]  = "Bearer \(t)"
//            }
            
        }
        
        return headers
    }
    
    
    
}

