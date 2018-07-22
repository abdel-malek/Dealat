//
//  Cat.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class Cat : BaseEntity {
    
    var category_id : JSON!
    var category_name : String!
    var parent_id : JSON!
    var web_image : String!
    var mobile_image : String!
    var tamplate_name : String!
    var description : String!
    var tamplate_id : JSON!
    var children : [Cat] = [Cat]()
    var ads_count : JSON!
    
    var ads_count2 : Int{
       return self.getAllAdsCount() + self.ads_count.intValue
    }
    
    var hidden_fields : String!

    
    // Mappable
    override func mapping(map: Map) {
        category_id <- map["category_id"]
        category_name <- map["category_name"]
        parent_id <- map["parent_id"]
        web_image <- map["web_image"]
        mobile_image <- map["mobile_image"]
        tamplate_name <- map["tamplate_name"]
        description <- map["description"]
        tamplate_id <- map["tamplate_id"]
        children <- map["children"]
        hidden_fields <- map["hidden_fields"]
        ads_count <- map["ads_count"]
        
        if ads_count == nil{
            ads_count = JSON(0)
        }
        
    }
    
    static func getName(_ category_id : Int) -> String{
        
        var n = [String]()
        
        if let f = Provider.shared.catsFull.filter({$0.category_id.intValue == category_id}).first{
            n.append(f.category_name!)
            
            if let f2 = Provider.shared.catsFull.filter({$0.category_id.intValue == f.parent_id.intValue}).first{
                n.append("\(getName(f2.category_id.intValue))")
            }
        }
        
        if AppDelegate.isArabic(){
            return n.reversed().joined(separator: "-")
        }else{
            return n.joined(separator: "-")
        }
    }
    
    
    
    
    func getAllAdsCount() -> Int{
        var total : Int = 0
        
        for i in self.children{
            total += i.ads_count.intValue
            total += i.getAllAdsCount()
        }
        
        return total
    }

        
    
    
}

