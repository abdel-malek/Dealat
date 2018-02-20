//
//  NewAddBaesVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class NewAddBaesVC: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        self.title = "Add new"
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let i = segue.destination as? NewAddVC{
            self.addChildViewController(i)
        }
    }


}
