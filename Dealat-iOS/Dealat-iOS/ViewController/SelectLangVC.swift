//
//  SelectLangVC.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 4/25/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class SelectLangVC: BaseVC,UITableViewDelegate,UITableViewDataSource {

    @IBOutlet weak var tableView : UITableView!
    @IBOutlet weak var okBtn: UIButton!

    var langs = ["EN".localized,"AR".localized]
    var selected : Int!

    
    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.tableFooterView = UIView()
        self.okBtn.isHidden = true
        
        Provider.setScreenName("LanguagesActivity")
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.langs.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        cell.selectionStyle = .none
        cell.textLabel?.text = self.langs[indexPath.row]
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        for i in tableView.visibleCells{
            i.accessoryType = .none
        }
        if let cell = tableView.cellForRow(at: indexPath){
            cell.accessoryType = .none
            cell.accessoryType = .checkmark
            cell.tintColor = .white
            self.selected = indexPath.row
            self.okBtn.isHidden = false
        }
    }
    
    @IBAction func okAction(){
        
        if let i = self.selected {

            let lang = (i == 0) ?  "en" : "ar"
            
            UserDefaults.standard.setValue([lang], forKey: "AppleLanguages")
            UserDefaults.standard.synchronize()

            Provider.setLang(lang)
            Provider.isArabic = AppDelegate.isArabic()
            
            AppDelegate.setupViews()
            

//            let vc = self.storyboard?.instantiateViewController(withIdentifier: "RegisterVC") as! RegisterVC
//            self.navigationController?.pushViewController(vc, animated: true)
            
        }
        
    }
    
    
    
}
