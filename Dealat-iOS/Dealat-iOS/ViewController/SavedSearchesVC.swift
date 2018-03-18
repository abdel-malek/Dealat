//
//  SavedSearchesVC.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/18/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class SavedSearchesVC: BaseVC {

    @IBOutlet weak var tableView : UITableView!
    var homeVC : HomeVC!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        getData()
    }
    
    override func getRefreshing() {
        Communication.shared.get_my_bookmarks { (res) in
            self.hideLoading()
            
            
        }
    }


}
