//
//  AdDetailsBaseVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/12/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class AdDetailsBaseVC: UIViewController {

    var ad : AD2!
    var tamplateId : Int = -1

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = self.ad.title
        
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let i = segue.destination as? AdDetailsVC{
            i.ad = self.ad
//            i.tamplateId = self.tamplateId
            
//            var cat = ""
//            cat += (ad.parent_category_name != nil) ? "\(ad.parent_category_name!)-" : ""
//            cat += (ad.category_name != nil) ? "\(ad.category_name!)" : ""
//            i.category_full_name = "\(cat)"
            
            self.addChildViewController(i)
        }
    }
    

}
