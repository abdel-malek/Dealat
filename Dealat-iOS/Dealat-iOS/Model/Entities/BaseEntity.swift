//
//  BaseEntity.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class BaseEntity : Mappable{
    
    public func mapping(map: Map) {
        
    }
    
    public required init?(map: Map) {
        
    }
    
    init() {
        
    }
    
    
    let transformInt = TransformOf<Int, String>(fromJSON: { (value: String?) -> Int? in
        // transform value from String? to Int?
        return Int(value!)
    }, toJSON: { (value: Int?) -> String? in
        // transform value from Int? to String?
        if let value = value {
            return String(value)
        }
        return nil
    })
    
    let transformDouble = TransformOf<Double, String>(fromJSON: { (value: String?) -> Double? in
        // transform value from String? to Int?
        return Double(value!)
    }, toJSON: { (value: Double?) -> String? in
        // transform value from Int? to String?
        if let value = value {
            return String(value)
        }
        return nil
    })
    
    
}

