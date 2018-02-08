//
//  SideMenuVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/6/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class SideMenuVC: BaseVC {

    @IBOutlet weak var img : UIImageView!
    @IBOutlet var btns: [UIButton]!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        
    }
    
    override func setupViews() {
        DispatchQueue.main.async {
            self.img.layer.cornerRadius = self.img.bounds.width / 2
        }
        
        for i in btns{
            i.addTarget(self, action: #selector(self.didSelect), for: .touchUpInside)
        }
    }
    
    @objc @IBAction func didSelect(_ i : UIButton){
        refreshBtns()
        
        UIView.animate(withDuration: 0.2) {
            i.backgroundColor = Theme.Color.red
            i.setTitleColor(Theme.Color.White, for: .normal)
        }
    }
    
    func refreshBtns(){
        for i in btns{
            i.backgroundColor = .clear
            i.setTitleColor(Theme.Color.darkGrey, for: .normal)
        }
    }


}
